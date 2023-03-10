package com.example.celebrity_management.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.celebrity_management.model.BaseModel;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;



@Aspect
@Slf4j
@Component
public class CelebrityAspect {

  // JoinPoint => Point of method execution -> save() method is joinPoint

  @Before(value = "execution(* com.example.celebrity_management.repository.*.save*(..)) && args(baseModel)")
  public void onSave(BaseModel baseModel) {
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
