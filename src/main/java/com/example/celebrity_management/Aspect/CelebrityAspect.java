package com.example.celebrity_management.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.celebrity_management.model.BaseModel;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.aspectj.lang.JoinPoint;


@Aspect
@Slf4j
@Component
public class CelebrityAspect {

  // JoinPoint => Point of method execution -> save() method is joinPoint

  @Before(value = "execution(* com.example.celebrity_management.Repository.CelebrityRepository.save(..)) && args(baseModel)")
  public void onSave(JoinPoint joinPoint, BaseModel baseModel) {
    try{
      if (StringUtils.hasText(baseModel.getId())){
        baseModel.setUpdatedDate(new Date());
      } else {
        baseModel.setCreatedDate(new Date());
      }
    } catch (Exception exception){
      log.error("Aspect Error", exception);
    }
  }
  
}
