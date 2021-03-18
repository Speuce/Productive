package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A production-grade and persistent implementation of the
 * {@link ITaskManager} interface
 */
public class PersistentTaskSorter implements ITaskSorter {

    private IDataManager data;

    public PersistentTaskSorter(IDataManager data) {
        this.data = data;
    }


    @Override
    public void getTasksByPriority(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, outputparam);
    }

    @Override
    public void getTasksByCreation(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            ret.sort((a, b) -> b.getCreatedTime().compareTo(a.getCreatedTime()));
            outputparam.accept(ret);
        });
    }

    @Override
    public void getTasksByDueDate(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            ret.sort(
                    (a, b) -> {
                        if(a.getDueDate() != null){
                            if(b.getDueDate() != null){
                                return a.getDueDate().compareTo(b.getDueDate());
                            }else{
                                return -1;
                            }
                        }else{
                            if(b.getDueDate() == null){
                                return 0;
                            }else
                                return 1;
                        }
                    }
            );
            outputparam.accept(ret);
        });
    }

    @Override
    public void getTasksOnDate(LocalDate d, Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            outputparam.accept(ret.stream().filter(
                    task ->task.getDueDate() != null && task.getDueDate().equals(d)
            ).collect(Collectors.toList()));
        });
    }

    @Override
    public void getDaysWithTaskInMonth(LocalDate startofMonth, Consumer<LocalDate> outputparam) {
        YearMonth month = YearMonth.from(startofMonth);
        for(int i = 0; i < month.lengthOfMonth(); i++){
            LocalDate curr = startofMonth.plusDays(i);
            getTasksOnDate(curr, list ->{
                if(!list.isEmpty()){
                    outputparam.accept(curr);
                }
            });
        }
    }

    @Override
    public void getCompletedTasks(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(true, outputparam);
    }

}
