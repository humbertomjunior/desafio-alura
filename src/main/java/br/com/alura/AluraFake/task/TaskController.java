package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.OpenTextTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.task.response.CreateTaskResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;



    @Transactional
    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity createMultipleChoiceTask(@RequestBody @Valid MultipleChoiceTask multipleChoiceTask) {
        return ResponseEntity.status(201).body(this.taskService.createNewTask(multipleChoiceTask, TaskType.MULTIPLE_CHOICE));
    }

    @Transactional
    @PostMapping("/task/new/opentext")
    public ResponseEntity<CreateTaskResponse> taskNewOpentextPost(@RequestBody @Valid OpenTextTask openTextAnswer) {
        return ResponseEntity.status(201).body(this.taskService.createNewTask(openTextAnswer, TaskType.OPEN_TEXT));
    }

    @Transactional
    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<CreateTaskResponse> taskNewSinglechoicePost(@RequestBody @Valid SingleChoiceTask singleChoiceTask) {
        return ResponseEntity.status(201).body(this.taskService.createNewTask(singleChoiceTask, TaskType.SINGLE_CHOICE));
    }
}