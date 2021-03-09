package com.productive6.productive.logic.task.impl;

import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.datamanage.IDataManager;

import java.time.LocalDate;
import java.util.List;
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
            ret.sort((a, b) -> (int) (a.getCreatedTime() - b.getCreatedTime()));
            outputparam.accept(ret);
        });
    }

    @Override
    public void getTasksByDueDate(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            ret.sort((a, b) -> a.getDueDate().compareTo(b.getDueDate()));
            outputparam.accept(ret);
        });
    }

    @Override
    public void getTasksOnDate(LocalDate d, Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(false, ret ->{
            outputparam.accept(ret.stream().filter(
                    task ->task.getDueDate().equals(d)
            ).collect(Collectors.toList()));
        });
    }

    @Override
    public void getCompletedTasks(Consumer<List<Task>> outputparam) {
        data.task().getAllTasks(true, outputparam);
    }

}
