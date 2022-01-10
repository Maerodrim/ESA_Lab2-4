package com.ssau.esalab.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssau.esalab.model.Car;
import com.ssau.esalab.model.Model;
import com.ssau.esalab.services.CarService;
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
@RequestMapping(value = "/car")
public class CarController {

    private CarService carService;
    private ModelService modelService;

    @Autowired
    public CarController(CarService carService, ModelService modelService) {
        this.carService = carService;
        this.modelService = modelService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "accept=application/json")
    public ResponseEntity getCar() throws JsonProcessingException {
        List<Car> cares = carService.findAll();

        return ResponseEntity.ok().body(cares);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "accept=application/xml")
    public ModelAndView getCarXSLT() throws JsonProcessingException {
        List<Car> cares = carService.findAll();
        ModelAndView modelAndView = new ModelAndView("car");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(cares)));
        modelAndView.addObject(source);
        return modelAndView;
    }


    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    public ResponseEntity getCarById(@PathVariable("carId") Integer carId) {

        Optional<Car> car = carService.findById(carId);
        if (!car.isPresent()) {
            return new ResponseEntity<Object>(String.format("car with id %s not found", carId), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(car.get());
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewCar(@RequestBody Car car, @RequestParam Integer modelId) {

        return getResponseEntity(modelId, car);
    }


    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    public ResponseEntity updateCar(
            @PathVariable("carId") Integer carId,
            @RequestParam(defaultValue = "") Integer modelId,
            @RequestBody Car car) {
        Optional<Car> excar = carService.findById(carId);
        if (!excar.isPresent() && modelId > 0) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", carId), HttpStatus.NOT_FOUND);
        }
        Car targetcar = excar.get();

        if (!car.getColor().isEmpty()) {
            targetcar.setColor(car.getColor());
        }
        if (!(car.getCost() == null)) {
            targetcar.setCost(car.getCost());
        }
        if (!(car.getNumberD() == null)) {
            targetcar.setNumberD(car.getNumberD());
        }

        return getResponseEntity(modelId, targetcar);
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCar(@PathVariable("carId") Integer carId) {
        Optional<Car> car = carService.findById(carId);
        if (!car.isPresent()) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", carId), HttpStatus.NOT_FOUND);
        }
        carService.delete(car.get());
        return ResponseEntity.ok().build();
    }

    private ResponseEntity getResponseEntity(@RequestParam(defaultValue = "") Integer modelId, Car targetСar) {
        Optional<Model> model = modelService.findById(modelId);
        if (!model.isPresent()) {
            return new ResponseEntity<Object>(String.format("Model with id %s not found", modelId), HttpStatus.NOT_FOUND);
        }
        targetСar.setModel(model.get());

        carService.save(targetСar);
        return ResponseEntity.ok().build();
    }
}


