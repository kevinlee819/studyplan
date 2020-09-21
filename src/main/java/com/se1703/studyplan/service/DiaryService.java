package com.se1703.studyplan.service;

import com.se1703.core.Utils.MongoUtils;
import com.se1703.core.Utils.TimeUtils;
import com.se1703.studyplan.entity.Diary;
import com.se1703.studyplan.entity.VOs.CreateDiary;
import com.se1703.studyplan.entity.VOs.ShowDiary;
import com.se1703.studyplan.entity.VOs.TimeVO;
import com.se1703.studyplan.mapper.DiaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/16 15:52
 **/
@Service
public class DiaryService {
    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private AuthService authService;

    public String addDiary(CreateDiary createDiary){
        Diary diary = new Diary();
        diary.setTitle(createDiary.getTitle());
        diary.setContent(createDiary.getContent());
        diary.setUserId(authService.getCurrentUser().getUserId());
        return diaryMapper.saveOne(diary);
    }

    public List<ShowDiary> getDiary(){
        List<Diary> diaries = diaryMapper.findByUserId(authService.getCurrentUser().getUserId());
        return diary2Show(diaries);
    }

    private List<ShowDiary> diary2Show(List<Diary> diaries){
        if (diaries == null || diaries.isEmpty()){
            return null;
        }
        List<ShowDiary> showDiaries = new ArrayList<>();
        for (Diary diary : diaries) {
            ShowDiary showDiary = new ShowDiary();
            showDiary.setDate(MongoUtils.objectId2Date(diary.getId()));
            showDiary.setContent(diary.getContent());
            showDiary.setTitle(diary.getTitle());
            showDiary.setId(diary.getId());
            showDiaries.add(showDiary);
        }
        return showDiaries;
    }

    public List<ShowDiary> getDiary(TimeVO timeVO) throws ParseException {
        List<Diary> diaries = diaryMapper.getDiaryByDate(timeVO.getStartTime(), TimeUtils.genFullClockDate(timeVO.getEndTime()),authService.getCurrentUser().getUserId());
        return diary2Show(diaries);
    }

    public boolean delById(String id){
        return diaryMapper.deleteById(id);
    }

    public boolean updateDiary(ShowDiary showDiary){
        return diaryMapper.updateDiary(showDiary);
    }


    public boolean delByUserId(String userId){
        return diaryMapper.delByUserId(userId);
    }
}
