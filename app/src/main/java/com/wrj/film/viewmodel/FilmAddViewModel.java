package com.wrj.film.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tool.util.DataUtils;
import com.wrj.film.BR;

/**
 * Created by Administrator on 2017/12/24.
 */

public class FilmAddViewModel extends BaseObservable {
    private String title;
    private String type;
    private String money;
    private String introduction;
    private String duration;
    private boolean isNowShowing;//是否热映
    private boolean isBanner;//是否轮播
    private String dates;
    private String times;
    private String photoUrl;
    private int dateSize = 0;
    private int timeSize = 0;

    public int getDateSize() {
        return dateSize;
    }

    public void setDateSize(int dateSize) {
        if (dateSize == 0) this.dateSize = 0;
        else
            this.dateSize += dateSize;
    }

    public int getTimeSize() {
        return timeSize;
    }

    private void setTimeSize(int timeSize) {
        if (timeSize == 0) this.timeSize = 0;
        else this.timeSize += timeSize;
    }

    @Bindable
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        notifyPropertyChanged(BR.photoUrl);
    }

    @Bindable
    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        if (DataUtils.checkStrNotNull(dates)) {
            if (DataUtils.checkStrNotNull(this.dates)) {
                this.dates = this.dates + "、" + dates;
            } else
                this.dates = dates;
            setDateSize(1);
            notifyPropertyChanged(BR.dates);
        } else {
            this.dates = dates;
            setDateSize(0);
        }
    }

    @Bindable
    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        if (DataUtils.checkStrNotNull(times)) {
            if (DataUtils.checkStrNotNull(this.times)) {
                this.times = this.times + "、" + times;
            } else
                this.times = times;
            setTimeSize(1);
            notifyPropertyChanged(BR.times);
        } else {
            this.times = times;
            setTimeSize(0);
        }
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
        notifyPropertyChanged(BR.money);
    }

    @Bindable
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
        notifyPropertyChanged(BR.introduction);
    }

    @Bindable
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
        notifyPropertyChanged(BR.duration);
    }

    @Bindable
    public boolean isNowShowing() {
        return isNowShowing;
    }

    public void setNowShowing(boolean nowShowing) {
        isNowShowing = nowShowing;
        notifyPropertyChanged(BR.nowShowing);
    }

    @Bindable
    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
        notifyPropertyChanged(BR.banner);
    }
}
