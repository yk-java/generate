package com.example.demo.controller;


import com.example.demo.generate.CodeGenerationBuilder;
import com.example.demo.generate.GeneratorConfig;
import com.example.demo.generate.GeneratorParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 透明图白底图报表查询 控制类
 *
 * @author yk
 */
@Controller
@RequestMapping("/")
public class GeneratorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorController.class);

    @Autowired
    protected GeneratorConfig generatorConfig;

    /**
     * 数据
     *
     * @param generatorParam   参数
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public Object getCode(GeneratorParam generatorParam) {

        CodeGenerationBuilder.getInstance(generatorParam, generatorConfig).codeGenerate();

        return "创建成功";
    }
}
