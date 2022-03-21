package com.example.zatch.navigation.chat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.ServiceType;
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
    private int height;
    private int buttonNewTop;
    private BottomSheetBehavior behavior;
    private ServiceType type;
    public boolean doFirstDrag = false;
    int navigationHeight;

    //item 선택 위한 변수
    public static final int NULL = -1;
    public int selectItemPosition = NULL;

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
        type = ((MakeMeetingBottomSheet) getParentFragment().getParentFragment()).getType();
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
        System.out.println(height);
        navigationHeight = pxClass.getNavigationBarHeight();
        int statusHeight = pxClass.getStatusBarHeight();
        System.out.println(navigationHeight);

        int buttonHeight = (int)pxClass.returnPx(72);


        View behaviourView = ((MakeMeetingBottomSheet) getParentFragment().getParentFragment()).sendBehaviourView();
        float viewHeight = behaviourView.getY() + behaviourView.getHeight();
        behavior = BottomSheetBehavior.from(behaviourView);

        int peekHeight = height/2;
        buttonNewTop = peekHeight - buttonHeight;
//        int buttonTop = (int)viewHeight - buttonHeight;
        //bottomsheet height, display height 다른 이유는 navigation bar size 때
//        int buttonTop = (int)height - 2*buttonHeight;
        int buttonTop = (int)height - buttonHeight - navigationHeight - statusHeight;

        behavior.setPeekHeight(peekHeight);

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                doFirstDrag = true;
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    Log.e("expand","expand");
                    binding.placeCheckFinishButton.setY(buttonTop);
                }else if(newState == BottomSheetBehavior.STATE_SETTLING){
                    Log.e("settling","settling");
                    binding.placeCheckFinishButton.setY(buttonNewTop);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        if(type == ServiceType.Gatch)
            initColorByType();

    }

    private void initColorByType(){
        binding.searchTextField.setBackground(getResources().getDrawable(R.drawable.text_background_stroke_yellow_8));
        binding.placeCheckFinishButton.setBackground(getResources().getDrawable(R.drawable.button_yellow));

        for(Drawable d :binding.searchTextField.getCompoundDrawables()) {
            if(d != null)
                d.setTint(getResources().getColor(R.color.zatch_yellow));
        }
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
                int recyclerviewHeight = height - binding.c0.getHeight() - navigationHeight;
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

                    //처음 검색결과 보였을 때, 완료 버튼 높이 설정 코
                    if(!doFirstDrag)
                        binding.placeCheckFinishButton.setY(buttonNewTop);
                    //item 선택하는 경우 짜기
                    if(selectItemPosition != NULL) {
//                        binding.placeCheckFinishButton.setY(buttonNewTop);
                        if (selectItemPosition != getAdapterPosition()) {
                            view.setBackgroundColor(getResources().getColor(R.color.black_5));
                            selectItemPosition = getAdapterPosition();
                            checkedView.setBackgroundColor(getResources().getColor(R.color.white));
                            checkedView = this.view;
                            binding.placeCheckFinishButton.setVisibility(View.VISIBLE);
                        } else {
                            selectItemPosition = NULL;
                            checkedView = null;
                            view.setBackgroundColor(getResources().getColor(R.color.white));
                            binding.placeCheckFinishButton.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        selectItemPosition = getAdapterPosition();
                        view.setBackgroundColor(getResources().getColor(R.color.black_5));
                        binding.placeCheckFinishButton.setVisibility(View.VISIBLE);
                        checkedView = this.view;
                    }

                });
            }

            public void setData(SearchPlaceData data){
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
