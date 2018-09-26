package com.clipicker.me.web.controller;




import com.clipicker.me.service.SymptomService;
import com.clipicker.me.web.model.AjaxResult;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/symptom")
public class SymptomController extends BaseController{

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult listSymptom(@RequestParam(value="name", required = false) String name){
        Map<String, Object> filter = new HashMap<>();
        if(!StringUtils.isEmpty(name)){
            filter.put("name", name);
        }
        return AjaxResult.success(symptomService.list(filter));
    }

}
