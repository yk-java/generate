package ${bussPackage}.${entityPackage}.action;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${bussPackage}.common.BaseAction;
import ${bussPackage}.common.Page;
import ${bussPackage}.${entityPackage}.bean.${className};
import ${bussPackage}.${entityPackage}.service.${className}Service;
import ${bussPackage}.common.util.StringUtils;

/**
 * 
 * ${codeName} 控制类
 * @author ${developer}
 * 
 */ 
@Controller
@RequestMapping("${entityPackage}/${lowerName}") 
public class ${className}Action extends BaseAction<${className}>{
	private static final Log log = LogFactory.getLog(${className}Action.class);
	
	@Resource
	private ${className}Service ${lowerName}Service; 
	
	/**
	 * 跳转到列表jsp
	 * @return
	 */
	@RequestMapping("${lowerName}")
	public String index(){
		return "${entityPackage}/${lowerName}/${lowerName}";
	}
	
	/**
	 * 添加(修改)窗口
	 * @return
	 */
	@RequestMapping("input")
	public ModelAndView input(Integer id){
		${className} ${lowerName} = new ${className}();
		if(id != null){
			${lowerName} = ${lowerName}Service.get(id);
		}
		return new ModelAndView("${entityPackage}/${lowerName}/${lowerName}-input").addObject("${lowerName}", ${lowerName});
	}
	
	/**
	 * 获取信息列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("list")
	//@ResponseBody表示该方法的返回结果直接写入HTTP response body中, 会直接返回json数据
	@ResponseBody
	public String getList(int page,int rows){
		int pageNo = page * rows - rows;
		int pageSize = rows;
		
		Page<${className}> pages = new Page<${className}>();//每页记录
		pages.setPageNo(pageNo);
		pages.setPageSize(pageSize);
		
		pages = ${lowerName}Service.getAll(pages);
		
		String outStr = transToJsonString(pages);
		return outStr;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(${className} ${lowerName}){
		
		//该名称已存在
		if(${lowerName}Service.checkIsExist(${lowerName}) > 0){
			return "-1";
		}
		
		try {
			if(${lowerName}.getId() == 0){
				${lowerName}Service.save(${lowerName});
			}else{
				${lowerName}Service.update(${lowerName});
			}
			
			return "0";
		} catch (Exception e) {
			log.error("error", e);
			return "-2";
		}
	}
	
	
	/**
	 *删除信息 
	 * @param idsStr
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(String ids){
		try {
			${lowerName}Service.delete(StringUtils.stringsToList(ids));
		} catch (Exception e) {
			log.error(e);
			return "-1";
		}
		//返回0到页面上表示删除成功
		return "0";
	}
}
