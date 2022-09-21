package com.khodabandelu.iot.publisher.api.controller;


import com.khodabandelu.iot.publisher.api.dto.BaseResponse;
import com.khodabandelu.iot.publisher.api.dto.ListTaskResponse;
import com.khodabandelu.iot.publisher.domain.IotGroupTask;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.service.IotTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/iot/tasks")
@RequiredArgsConstructor
public class IotTaskController {
    private final Logger logger = Logger.getLogger(IotTaskController.class.getName());
    private final IotTaskService iotTaskService;

    /**
     * {@code GET  /api/iot/tasks} : list iot task.
     *
     * @return list tasks
     */
    @GetMapping()
    public ResponseEntity<BaseResponse> listTasks() {
        try {
            var list = this.iotTaskService.listTasks();
            return new ResponseEntity<>(new ListTaskResponse("list task request completed successfully!", list), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrMessage = "Error while processing request to create task ";
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code POST  /api/iot/tasks/group} : create iot task.
     *
     * @param groupTask is the data of job
     * @return message success if  request completed successfully.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     */
    @PostMapping("/group")
    public ResponseEntity<BaseResponse> createTask( @Valid @RequestBody IotGroupTask groupTask) {
        try {
            var list = this.iotTaskService.createGroupTask(groupTask);
            return new ResponseEntity<>(new ListTaskResponse(groupTask.getId(),"Create  group task request completed successfully!", list ), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = "Error while processing request to create group task ";
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code POST  /api/iot/tasks} : create iot task.
     *
     * @param task is the data of job
     * @return message success if  request completed successfully.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     */
    @PostMapping()
    public ResponseEntity<BaseResponse> createTask(@Valid @RequestBody IotTask task) {
        try {
            this.iotTaskService.createTask(task);
            return new ResponseEntity<>(new BaseResponse(task.getId(), "Create task request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = "Error while processing request to create task ";
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code Delete  /api/iot/tasks} : delete iot task.
     *
     * @param taskId is the id of job
     * @return message success if  request completed successfully.
     * @throws IllegalStateException {@code 400 (Bad Request)} if the data is not correct.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<BaseResponse> removeScheduledTask(@PathVariable("taskId") String taskId) {
        try {
            this.iotTaskService.removeScheduledTask(taskId);
            return new ResponseEntity<>(new BaseResponse("Delete task request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrMessage = "Error while processing request to delete task ";
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
