package com.pyrtoper.dictionary.entity;

import com.pyrtoper.dictionary.constant.WorkState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_states")
public class WorkStates {

  @Id
  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "work_state")
  @Enumerated(EnumType.STRING)
  private WorkState workState = WorkState.POLISH_TO_RUSSIAN;

  public WorkStates() {
  }

  public WorkStates(long chatId, WorkState workState) {
    this.chatId = chatId;
    this.workState = workState;
  }

  public WorkStates(long chatId) {
    this.chatId = chatId;
  }

  @Override
  public String toString() {
    return "WorkStates{" +
        "chatId=" + chatId +
        ", workState=" + workState +
        '}';
  }

  public WorkState getWorkState() {
    return workState;
  }
}
