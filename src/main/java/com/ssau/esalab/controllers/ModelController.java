package com.ssau.esalab.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssau.esalab.model.Manager;
import com.ssau.esalab.model.Model;
import com.ssau.esalab.services.ManagerService;
import com.ssau.esalab.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/model")
public class ModelController {

    private ManagerService managerService;
    private ModelService modelService;

    @Autowired
    public ModelController(ManagerService managerService, ModelService modelService) {
        this.managerService = managerService;
        this.modelService = modelService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,headers = "accept=application/json")
    public ResponseEntity getModel() throws JsonProcessingException{
        List<Model> models = modelService.findAll();

        return ResponseEntity.ok().body(models);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET,headers = "accept=application/xml")
    public ModelAndView getModelXSLT() throws JsonProcessingException {
        List<Model> models = modelService.findAll();
        ModelAndView modelAndView=new ModelAndView("model");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(models)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{modelId}", method = RequestMethod.GET)
    public ResponseEntity getModelById(@PathVariable("modelId") Integer modelId) {

        Optional<Model> model = modelService.findById(modelId);
        if (!model.isPresent()) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", modelId), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(model.get());
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewModel(
            @RequestBody Model model,
            @RequestParam Integer managerId) {

        Optional<Manager> manager = managerService.findById(managerId);
        if (!manager.isPresent()) {
            return new ResponseEntity<Object>(String.format("Manager with id %s not found", managerId), HttpStatus.NOT_FOUND);
        }
        model.setManager(manager.get());
        modelService.save(model);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{modelId}", method = RequestMethod.PUT)
    public ResponseEntity updateModel(
            @RequestBody Model model,
            @PathVariable("modelId") Integer modelId,
            @RequestParam(defaultValue = "") Integer managerId) {

        Optional<Model> exmodel = modelService.findById(modelId);
        if (!exmodel.isPresent()) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", modelId), HttpStatus.NOT_FOUND);
        }
        Model targetModel = exmodel.get();

        Optional<Manager> manager = managerService.findById(managerId);
        if (!manager.isPresent()) {
            return new ResponseEntity<Object>(String.format("Manager with id %s not found", managerId), HttpStatus.NOT_FOUND);
        }
        targetModel.setManager(manager.get());

        if (!model.getFullName().isEmpty()) {
            targetModel.setFullName(model.getFullName());
        }
        if (!model.getPhone().isEmpty()) {
            targetModel.setFullName(model.getPhone());
        }
        if (model.getAge()!=null) {
            targetModel.setAge(model.getAge());
        }

        if (model.getBody()!=null) {
            targetModel.setBody(model.getBody());
        }
        if ((model.getBrand()!=null)) {
            targetModel.setBrand(model.getBrand());
        }
        if (model.getContractNumber()>0) {
            targetModel.setContractNumber(model.getContractNumber());
        }

        modelService.save(targetModel);
        return ResponseEntity.ok().build();

    }

    @RequestMapping(value = "/{modelId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteModel(@PathVariable("modelId") Integer modelId) {
        Optional<Model> exmodel = modelService.findById(modelId);
        if (!exmodel.isPresent()) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", modelId), HttpStatus.NOT_FOUND);
        }
        modelService.delete(exmodel.get());
        return ResponseEntity.ok().build();
    }
}
