package com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;

/**
 * Created by thuanle on 2/12/17.
 */
public class PuzzleAdmobNativeAdAdapter extends RecyclerViewAdapterWrapper {

	private static final int TYPE_FB_NATIVE_ADS = 900;
	private static final int DEFAULT_AD_ITEM_INTERVAL = 4;

	private final Param mParam;

	private PuzzleAdmobNativeAdAdapter(Param param) {
		super(param.adapter);
		this.mParam = param;

		assertConfig();
		setSpanAds();
	}

	private void assertConfig() {
		if (mParam.gridLayoutManager != null) {
			//if user set span ads
			int nCol = mParam.gridLayoutManager.getSpanCount();
			if (mParam.adItemInterval % nCol != 0) {
				throw new IllegalArgumentException(String.format("The adItemInterval (%d) is not divisible by number of columns in GridLayoutManager (%d)", mParam.adItemInterval, nCol));
			}
		}
	}

	private int convertAdPosition2OrgPosition(int position) {

		return position - (position + 1) / (mParam.adItemInterval + 1);
	}

	@Override
	public int getItemCount() {
		int realCount = super.getItemCount();
		return realCount + realCount / mParam.adItemInterval;
	}

	@Override
	public int getItemViewType(int position) {
		if (isAdPosition(position)) {
			return TYPE_FB_NATIVE_ADS;
		}
		return super.getItemViewType(convertAdPosition2OrgPosition(position));
	}

	private boolean isAdPosition(int position) {
		/*if(position==1|| position==4)return true;*/
		return (position + 1) % (mParam.adItemInterval + 1) == 0;
	}

	public static final boolean isValidPhoneNumber(CharSequence target) {
		if (target.length() != 10) {
			return false;
		} else {
			return android.util.Patterns.PHONE.matcher(target).matches();
		}
	}

	private void onBindAdViewHolder(final RecyclerView.ViewHolder holder) {
		final AdViewHolder adHolder = (AdViewHolder) holder;
		if (mParam.forceReloadAdOnBind || !adHolder.loaded) {

		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == TYPE_FB_NATIVE_ADS) {
			onBindAdViewHolder(holder);
		} else {
			super.onBindViewHolder(holder, convertAdPosition2OrgPosition(position));
		}
	}

	private RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View adLayoutOutline = inflater
				.inflate(mParam.itemContainerLayoutRes, parent, false);
		ViewGroup vg = adLayoutOutline.findViewById(mParam.itemContainerId);

		LinearLayout adLayoutContent = (LinearLayout) inflater
				.inflate(R.layout.puzzle_item_admob_native_ad, parent, false);
		vg.addView(adLayoutContent);
		return new AdViewHolder(adLayoutOutline);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == TYPE_FB_NATIVE_ADS) {
			return onCreateAdViewHolder(parent);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	private void setSpanAds() {
		if (mParam.gridLayoutManager == null) {
			return;
		}
		final GridLayoutManager.SpanSizeLookup spl = mParam.gridLayoutManager.getSpanSizeLookup();
		mParam.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				if (isAdPosition(position)) {
					return spl.getSpanSize(position);
				}
				return 1;
			}
		});
	}

	private static class Param {
		String admobNativeId;
		RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
		int adItemInterval;
		boolean forceReloadAdOnBind;

		int layout;

		@LayoutRes
		int itemContainerLayoutRes;

		@IdRes
		int itemContainerId;

		GridLayoutManager gridLayoutManager;
	}

	public static class Builder {
		private final Param mParam;

		private Builder(Param param) {
			mParam = param;
		}

		public static Builder with(String placementId, RecyclerView.Adapter wrapped, String layout) {
			Param param = new Param();
			param.admobNativeId = placementId;
			param.adapter = wrapped;


			if (layout.equalsIgnoreCase("small")) {
				param.layout = 0;
			} else if (layout.equalsIgnoreCase("medium")) {

				param.layout = 1;
			} else {
				param.layout = 2;

			}

			param.adItemInterval = DEFAULT_AD_ITEM_INTERVAL;
			param.itemContainerLayoutRes = R.layout.puzzle_item_admob_native_ad;
			param.itemContainerId = R.id.native_ad_container;
			param.forceReloadAdOnBind = true;
			return new Builder(param);
		}

		public Builder adItemInterval(int interval) {
			mParam.adItemInterval = interval;
			return this;
		}

		public Builder adLayout(@LayoutRes int layoutContainerRes, @IdRes int itemContainerId) {
			mParam.itemContainerLayoutRes = layoutContainerRes;
			mParam.itemContainerId = itemContainerId;
			return this;
		}

		public PuzzleAdmobNativeAdAdapter build() {
			return new PuzzleAdmobNativeAdAdapter(mParam);
		}

		public Builder enableSpanRow(GridLayoutManager layoutManager) {
			mParam.gridLayoutManager = layoutManager;
			return this;
		}

		public Builder adItemIterval(int i) {
			mParam.adItemInterval = i;
			return this;
		}

		public Builder forceReloadAdOnBind(boolean forced) {
			mParam.forceReloadAdOnBind = forced;
			return this;
		}
	}

	private static class AdViewHolder extends RecyclerView.ViewHolder {

		RelativeLayout layAds ;
		LinearLayout adContainer;
		boolean loaded;

		AdViewHolder(View view) {
			super(view);
			adContainer = view.findViewById(R.id.native_ad_container);
			layAds = view.findViewById(R.id.layAds);
			if (NetizenSettings.SELECT_MAIN_ADS.equals("ADMOB")){
				AliendroidNative.SmallNativeAdmobRectangle((Activity) getContext(), layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES,
						NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
			} else {
				AliendroidNative.SmallNativeMaxRectangle((Activity) getContext(), layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
			}

		}

		Context getContext() {
			return adContainer.getContext();
		}
	}
}
