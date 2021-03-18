package com.productive6.productive.dummyImp;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StreakSortManagerDummy implements ITaskSorter, ProductiveListener {

    ArrayList<Task> list;

    public StreakSortManagerDummy(){
        list = new ArrayList<>();
        EventDispatch.registerListener(this);
    }

    @Override
    public void getTasksByPriority(Consumer<List<Task>> outputparam) {

    }

    @Override
    public void getTasksByCreation(Consumer<List<Task>> outputparam) {

    }

    @Override
    public void getTasksByDueDate(Consumer<List<Task>> outputparam) {

    }

    @Override
    public void getTasksOnDate(LocalDate d, Consumer<List<Task>> outputparam) {

    }

    @Override
    public void getDaysWithTaskInMonth(LocalDate startofMonth, Consumer<LocalDate> outputparam) {

    }

    @Override
    public void getCompletedTasks(Consumer<List<Task>> outputparam) {
                outputparam.accept(list);
    }

    @ProductiveEventHandler
    public void addTask(DummyTaskEvent e){
       list.add(e.getTask());

    }

}
