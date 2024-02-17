package com.springBoot.AutomatedEmailApplication.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    public Status status;
    public List<T> data;
}
