package com.wrj.film.view.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.tool.util.CollectionUtils;
import com.tool.util.DataUtils;
import com.tool.util.DateUtils;
import com.tool.util.gallerfinal.GalleryFinalUtil;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityRootAddFilmBinding;
import com.wrj.film.model.FilmDate;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.model.FilmTime;
import com.wrj.film.model.SortTypeEnum;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.widget.DialogHelper;
import com.wrj.film.viewmodel.FilmAddViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class RootUpdateFilmActivity extends BaseActivity<ActivityRootAddFilmBinding, FilmAddViewModel> {
    private String photoPath;
    private List<FilmTime> timesAll;
    private List<FilmDate> datesAll;
    public static final String KEY_FILM_ID = "key_view_id";
    private boolean timeCloseLoading = false;
    private boolean dateCloseLoading = false;
    private boolean filmModelCloseLoading = false;
    private boolean isUpdateTime = false;
    private boolean isUpdateDate = false;
    private String id = "";
    private int year = Integer.valueOf(DateUtils.getNowYear()), mMonth = 0, day = 1;
    private boolean isRefreshDate = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_root_add_film;
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "编辑电影");
        ((CustomTitleBar) binding.titleBar).setRightTitle("完成");
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setData(viewModel);
        timesAll = new ArrayList<>();
        datesAll = new ArrayList<>();
        id = getIntent().getExtras().getString(KEY_FILM_ID);
        showLoading();
        queryFilm(id);
        queryFilmDate(id);
        queryFilmTime(id);
    }

    private void queryFilmTime(String filmId) {
        BmobQuery<FilmTime> query = new BmobQuery<>();
        final FilmModel filmModel = new FilmModel();
        filmModel.setObjectId(filmId);
        query.addWhereRelatedTo("times", new BmobPointer(filmModel));
        query.findObjects(new FindListener<FilmTime>() {
            @Override
            public void done(List<FilmTime> list, BmobException e) {
                timeCloseLoading = true;
                if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                    String times = "";
                    for (FilmTime time : list) {
                        timesAll.add(time);
                        if (DataUtils.checkStrNotNull(times))
                            times = times + "、" + time.getTime();
                        else
                            times = time.getTime();
                    }
                    viewModel.setTimes(times);
                    isUpdateTime = true;
                }
                checkCanCloseLoading();
            }
        });
    }

    private void checkCanCloseLoading() {
        if (filmModelCloseLoading && timeCloseLoading && dateCloseLoading) {
            closeLoading();
        }
    }

    private void queryFilmDate(String filmId) {
        BmobQuery<FilmDate> query = new BmobQuery<>();
        final FilmModel filmModel = new FilmModel();
        filmModel.setObjectId(filmId);
        query.addWhereRelatedTo("dates", new BmobPointer(filmModel));
        query.findObjects(new FindListener<FilmDate>() {
            @Override
            public void done(List<FilmDate> list, BmobException e) {
                dateCloseLoading = true;
                if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                    String dates = "";
                    for (FilmDate date : list) {
                        datesAll.add(date);
                        if (DataUtils.checkStrNotNull(dates))
                            dates = dates + "、" + date.getDate();
                        else
                            dates = date.getDate();
                    }
                    viewModel.setDates(dates, false);
                    isUpdateDate = true;
                }
                checkCanCloseLoading();
            }
        });
    }

    private void queryFilm(String filmId) {
        FilmModelUtil.getFilmModelFromId(filmId, new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> models) {
                filmModelCloseLoading = true;
                if (CollectionUtils.collectionState(models) == CollectionUtils.COLLECTION_UNEMPTY) {
                    FilmModel model = models.get(0);
                    viewModel.setType(model.getType());
                    viewModel.setPhotoUrl(model.getPhotoUrl());
                    viewModel.setBanner(model.isBanner());
                    viewModel.setDuration(model.getDuration());
                    viewModel.setIntroduction(model.getIntroduction());
                    viewModel.setMoney(model.getMoney());
                    viewModel.setNowShowing(model.isNowShowing());
                    viewModel.setTitle(model.getTitle());
                }
                checkCanCloseLoading();
            }
        });
    }

    @Override
    protected void initListener() {
        ((CustomTitleBar) binding.titleBar).setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFilm();
            }
        });
        binding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> ts = new ArrayList<>(SortTypeEnum.getAllType());
                ts.remove(0);//移除掉“全部”
                new DialogHelper().showListDialog(RootUpdateFilmActivity.this, "请选择电影类型", viewModel.getType(), ts, new DialogHelper.InputDialogCallBack() {
                    @Override
                    public void positive(String content) {
                        viewModel.setType(content);
                    }

                    @Override
                    public void negative() {

                    }
                });
            }
        });
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getDateSize() == 3) {
                    showToast("最多可添加3个上映日期");
                    return;
                }
                onMonthDayPicker();
            }
        });
        binding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimePicker();
            }
        });
        binding.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new GalleryFinalUtil(RootUpdateFilmActivity.this,
                        new GalleryFinal.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                if (resultList != null && resultList.size() > 0) {
                                    photoPath = resultList.get(0).getPhotoPath();
                                    viewModel.setPhotoUrl("file://" + photoPath);
                                    uploadFile();
                                }
                            }

                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {

                            }
                        }).gallerySingle(false);
            }
        });
    }

    private void addFilm() {
        if (checkFilmParam()) {
            if (viewModel.getPhotoUrl().contains("file")) {
                showToast("图片尚未上传成功，请稍后重试");
                return;
            }
            datesAll.clear();
            timesAll.clear();
            showLoading();
            insertTimes();
            insertDates();
        }
    }


    private void uploadFile() {
        final BmobFile bmobFile = new BmobFile(new File(photoPath));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    viewModel.setPhotoUrl(bmobFile.getFileUrl());
//                    insertTimes();
//                    insertDates();
                } else {
                    closeLoading();
                    Log.e(TAG, "上传图片失败" + e.getMessage());
                    showToast("上传图片失败" + e.getMessage());
                }
            }
        });
    }

    public boolean checkSpecialChar(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符
        String regEx = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private boolean checkFilmParam() {
        if (checkSpecialChar(viewModel.getTitle())) {
            showToast("电影名称不允许包含特殊字符");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getType())) {
            showToast("请选择电影类型");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getDuration())) {
            showToast("请输入电影时长");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getMoney())) {
            showToast("请输入票价");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getDates())) {
            showToast("请选择上映日期");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getTimes())) {
            showToast("请输入上映时间");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getIntroduction())) {
            showToast("请输入电影简介");
            return false;
        }
        if (!DataUtils.checkStrNotNull(viewModel.getPhotoUrl())) {
            showToast("请选择电影海报");
            return false;
        }
        return true;
    }

    private void onTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        TimePickerDialog pickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isUpdateTime) {
                    viewModel.setTimes("");
                    isUpdateTime = false;
                }
                viewModel.setTimes(hourOfDay + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        pickerDialog.show();
    }

    public void onMonthDayPicker() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (isUpdateDate) {
                    viewModel.setDates("", false);
                    isUpdateDate = false;
                }
                if (month < calendar.get(Calendar.MONTH) || dayOfMonth <= calendar.get(Calendar.DAY_OF_MONTH)) {
                    showToast("请选择大于今天的日期");
                    return;
                }
                viewModel.setDates(year + "-" + (month + 1) + "-" + dayOfMonth, isRefreshDate);
            }
        }, year, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    @Override
    protected FilmAddViewModel getViewModel() {
        return new FilmAddViewModel();
    }

    private void insertDates() {
        final List<BmobObject> dates = new ArrayList<>();
        for (String d : viewModel.getDates().split("、")) {
            FilmDate time = new FilmDate(d);
            dates.add(time);
        }
        new BmobBatch().updateBatch(dates).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        datesAll.clear();
                        for (int i = 0; i < list.size(); i++) {
                            FilmDate date = (FilmDate) dates.get(i);
                            date.setObjectId(list.get(i).getObjectId());
                            datesAll.add(date);
                        }
                        insertFilmModel();
                    }
                } else {
                    closeLoading();
                    Log.e(TAG, e.getMessage());
                    showToast("存储数据失败" + e.getMessage());
                }
            }
        });
    }

    private void insertTimes() {
        final List<BmobObject> times = new ArrayList<>();
        for (String t : viewModel.getTimes().split("、")) {
            FilmTime time = new FilmTime(t);
            times.add(time);
        }
        new BmobBatch().updateBatch(times).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    timesAll.clear();
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        for (int i = 0; i < list.size(); i++) {
                            FilmTime time = (FilmTime) times.get(i);
                            time.setObjectId(list.get(i).getObjectId());
                            timesAll.add(time);
                        }
                        insertFilmModel();
                    }
                } else {
                    closeLoading();
                    Log.e(TAG, e.getMessage());
                    showToast("存储数据失败" + e.getMessage());
                }
            }
        });
    }

    private void insertFilmModel() {
        if (CollectionUtils.collectionState(timesAll) == CollectionUtils.COLLECTION_UNEMPTY
                && CollectionUtils.collectionState(datesAll) == CollectionUtils.COLLECTION_UNEMPTY) {
            FilmModel filmModel = new FilmModel();
            filmModel.setType(viewModel.getType());
            filmModel.setTitle(viewModel.getTitle());
            filmModel.setNumber(0);
            filmModel.setPhotoUrl(viewModel.getPhotoUrl());
            filmModel.setNowShowing(viewModel.isNowShowing());
            filmModel.setMoney(viewModel.getMoney());
            filmModel.setIntroduction(viewModel.getIntroduction());
            filmModel.setDuration(viewModel.getDuration());
            filmModel.setBanner(viewModel.isBanner());
            BmobRelation r_dates = new BmobRelation();
            BmobRelation r_times = new BmobRelation();
            for (FilmTime time : timesAll) {
                r_times.add(time);
            }
            for (FilmDate date : datesAll) {
                r_dates.add(date);
            }
            filmModel.setTimes(r_times);
            filmModel.setDates(r_dates);
            filmModel.setObjectId(id);
            filmModel.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    closeLoading();
                    if (e == null) {
                        showToast("编辑电影成功");
                    } else {
                        Log.e(TAG, e.getMessage());
                        showToast("编辑电影失败" + e.getMessage());
                    }
                }
            });
        }
    }
}
