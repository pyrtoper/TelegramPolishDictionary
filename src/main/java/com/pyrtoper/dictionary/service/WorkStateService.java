package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.constant.WorkState;
import java.util.List;

public interface WorkStateService {
  String translate(String word);

  List<String> getSimilarWords(String initialWord);

  WorkState getType();
}
