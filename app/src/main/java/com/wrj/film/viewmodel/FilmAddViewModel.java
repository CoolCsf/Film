package com.wrj.film.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tool.util.DataUtils;
import com.tool.util.ToastHelp;
import com.wrj.film.BR;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    public void setDates(String dates, boolean refresh) {
        if (DataUtils.checkStrNotNull(dates)) {
            if (!refresh && DataUtils.checkStrNotNull(this.dates)) {
                if (this.dates.contains(dates)) {
                    ToastHelp.showToast("请勿重复添加相同的日期");
                    return;
                }
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
                if (this.times.contains(times)) {
                    ToastHelp.showToast("请勿重复添加相同的时间");
                    return;
                }
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

    public boolean checkFilmParam() {
        if (checkSpecialChar(title)) {
            ToastHelp.showToast("电影名称不允许包含特殊字符");
            return false;
        }
        if (!DataUtils.checkStrNotNull(title)) {
            ToastHelp.showToast("请输入电影名称");
            return false;
        }
        if (!DataUtils.checkStrNotNull(type)) {
            ToastHelp.showToast("请选择电影类型");
            return false;
        }
        if (!DataUtils.checkStrNotNull(duration)) {
            ToastHelp.showToast("请输入电影时长");
            return false;
        }
        if (!DataUtils.checkStrNotNull(money)) {
            ToastHelp.showToast("请输入票价");
            return false;
        }
        if (!DataUtils.checkStrNotNull(dates)) {
            ToastHelp.showToast("请选择上映日期");
            return false;
        }
        if (!DataUtils.checkStrNotNull(times)) {
            ToastHelp.showToast("请输入上映时间");
            return false;
        }
        if (!DataUtils.checkStrNotNull(introduction)) {
            ToastHelp.showToast("请输入电影简介");
            return false;
        }
        if (!DataUtils.checkStrNotNull(photoUrl)) {
            ToastHelp.showToast("请选择电影海报");
            return false;
        }
        return true;
    }

    public boolean checkSpecialChar(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符
        if (DataUtils.checkStrNotNull(str)) {
            String regEx = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.matches();
        }
        return false;
    }
}
