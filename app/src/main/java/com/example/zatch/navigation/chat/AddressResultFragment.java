package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.databinding.FragmentFindPlaceResultBinding;
import com.example.zatch.databinding.ItemFindPlaceResultBinding;
import com.example.zatch.navigation.chat.data.SearchPlaceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressResultFragment extends Fragment {

    private FragmentFindPlaceResultBinding binding;
    private List<SearchPlaceData> placeList;
    private String searchPlace;

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

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ItemFindPlaceResultBinding binding;

            public ViewHolder(View view) {
                super(view);
                this.binding = ItemFindPlaceResultBinding.bind(view);
            }

            public void setData(SearchPlaceData data){
                this.binding.placeName.setText(data.getPlaceName());
                this.binding.placeAddress.setText(data.getAddressName());
            }

            public String getPlaceName(){
                return this.binding.placeName.getText().toString();
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
            viewHolder.itemView.setOnClickListener(v->{
                Navigation.findNavController(getView())
                        .getBackStackEntry(R.id.makeMeetingFragment)
                        .getSavedStateHandle()
                        .set("result",viewHolder.getPlaceName());
                Navigation.findNavController(getView()).popBackStack();
            });
        }

        @Override
        public int getItemCount() {
            return placeList.size();
        }
    }

}
