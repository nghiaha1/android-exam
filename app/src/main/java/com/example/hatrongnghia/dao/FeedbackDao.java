package com.example.hatrongnghia.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hatrongnghia.entity.FeedbackEntity;

import java.util.List;

@Dao
public interface FeedbackDao {
    @Insert
    void insertFeedback(FeedbackEntity feedbackEntity);

    @Query("SELECT * FROM feedback")
    List<FeedbackEntity> getAllFeedback();
}
