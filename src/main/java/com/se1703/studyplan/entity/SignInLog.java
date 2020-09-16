package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author leekejin
 * @date 2020/9/16 15:36
 **/
@Data
@Document(collection = "sign_in_log")
public class SignInLog {
}
