package co.willnicholson.controller;

import co.willnicholson.services.SchedulerService;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {
    private SchedulerService schedulerService;

    public TaskController(SchedulerService s) throws Exception{
        this.schedulerService = s;
    }
}
