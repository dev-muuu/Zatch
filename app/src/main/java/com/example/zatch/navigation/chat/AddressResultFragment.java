package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.databinding.FragmentFindPlaceResultBinding;
import com.example.zatch.databinding.ItemFindPlaceResultBinding;
import com.example.zatch.navigation.chat.data.SearchPlaceData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressResultFragment extends Fragment {

    public FragmentFindPlaceResultBinding binding;
    private List<SearchPlaceData> placeList;
    private String searchPlace;
    public int selectItemPosition;
    private int height;
    private BottomSheetBehavior behavior;

    static final String BASE_URL = "https://dapi.kakao.com/";
    static final String API_KEY = "KakaoAK de8b17f677631d70f9545c4b19608dcf";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPlace = getArguments().getString("searchPlace");
        searchByKeyword(searchPlace);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFindPlaceResultBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchTextField.setText(searchPlace);
        binding.addressRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.placeCheckFinishButton.setOnClickListener(v->{
            Navigation.findNavController(getView())
                    .getBackStackEntry(R.id.makeMeetingFragment)
                    .getSavedStateHandle()
                    .set("result",placeList.get(selectItemPosition).getPlaceName());
            Navigation.findNavController(getView()).popBackStack();
        });

        //recyclerview height잡기
        ReturnPx pxClass = new ReturnPx(getActivity());
        height = pxClass.returnDisplayHeight();
        float buttonHeight = pxClass.returnPx(72);


        View behaviourView = ((MakeMeetingBottomSheet) getParentFragment().getParentFragment()).sendBehaviourView();
        float viewHeight = behaviourView.getY() + behaviourView.getHeight();
        float viewGetY = behaviourView.getY();
        behavior = BottomSheetBehavior.from(behaviourView);

        float buttonTop = viewHeight - buttonHeight;
        float buttonNewTop = viewGetY + buttonHeight;
        binding.placeCheckFinishButton.setY(buttonNewTop);

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_DRAGGING){
                    Log.e("drag","drag");
                }else if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    Log.e("expand","expand");
                    binding.placeCheckFinishButton.setY(buttonTop);
                }else if(newState == BottomSheetBehavior.STATE_SETTLING){
                    Log.e("settling","settling");
                    float newHeight = buttonNewTop;
                    binding.placeCheckFinishButton.setY(newHeight);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    //장소 검색
    class ResultSearchKeyword {
        List<SearchPlaceData> documents;
    }

    private void searchByKeyword(String keyword) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        KakaoApiService api = builder.build().create(KakaoApiService.class);
        Call<ResultSearchKeyword> call = api.getKakaoAddress(API_KEY, keyword);

        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                storePlaceData(response.body());
                int recyclerviewHeight = height - binding.c0.getHeight();
                binding.addressRecyclerview.getLayoutParams().height = recyclerviewHeight;
                binding.addressRecyclerview.setAdapter(new FindPlaceAdapter());
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                Log.w("통신실패", "Raw: ${response.raw()}");
            }
        });
    }

    private void storePlaceData(ResultSearchKeyword data) {
        this.placeList = data.documents;
    }

    public class FindPlaceAdapter extends RecyclerView.Adapter<FindPlaceAdapter.ViewHolder> {

        public View checkedView;

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ItemFindPlaceResultBinding adapterBinding;
            private View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                this.adapterBinding = ItemFindPlaceResultBinding.bind(view);
                view.setOnClickListener(v->{
                    if(selectItemPosition != getAdapterPosition()) {
                        view.setBackgroundColor(getResources().getColor(R.color.black_5));
                        selectItemPosition = getAdapterPosition();
                        binding.placeCheckFinishButton.setVisibility(View.VISIBLE);
                    }else{
                        selectItemPosition = -1;
                        view.setBackgroundColor(getResources().getColor(R.color.white));
                        binding.placeCheckFinishButton.setVisibility(View.GONE);
                    }

                });
            }

            public void setData(SearchPlaceData data){
                Log.e("2","2");
                this.view.setBackgroundColor(getResources().getColor(R.color.white));
                this.adapterBinding.placeName.setText(data.getPlaceName());
                this.adapterBinding.placeAddress.setText(data.getAddressName());

            }

            public String getPlaceName(){
                return this.adapterBinding.placeName.getText().toString();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_find_place_result, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.setData(placeList.get(position));
        }

        @Override
        public int getItemCount() {
            return placeList.size();
        }
    }

}
