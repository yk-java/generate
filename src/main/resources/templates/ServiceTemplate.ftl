package ${bussPackage}.service;

import javax.annotation.Resource;
import ${bussPackage}.vo.${className}VO;
import ${bussPackage}.mapper.${className}Mapper;
import ${bussPackage}.pojo.DeleteCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.pojo.PageData;
import com.example.demo.pojo.ResponseMessage;
import com.example.demo.pojo.UUIDUtils;


/**
 * 
 * ${codeName} 业务层
 * @author ${developer}
 * 
 */
@Service
public class ${className}Service {

    @Resource
    ${className}Mapper ${lowerName}Mapper;

    /**
    * 获取列表分页数据数据
    * @param query 参数
    * @return ResponseMessage
    */
    public ResponseMessage getPageData(${className}VO query) {
        ResponseMessage rm = ResponseMessage.getInstance();
        int offset = query.getRows() * (query.getPage() - 1);
        query.setOffset(offset);
        rm.setPageData(new PageData(${lowerName}Mapper.pageQuery(query), ${lowerName}Mapper.getRowNumber(query)));
        return rm;
    }
    /**
    * 根据主键获取详细数据
    * @param id 参数
    * @return ResponseMessage
    */
    public ResponseMessage getDetail(String id) {
        ResponseMessage rm = ResponseMessage.getInstance();
        rm.setData(${lowerName}Mapper.selectByPrimaryKey(id));
        return rm;
    }

    /**
    * 保存和更新方法
    * @param query 参数
    * @return ResponseMessage
    */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseMessage saveData(${className}VO query) {

        ResponseMessage rm = query.validate();
        if (rm.hasError()) {
            return rm;
        }
        if (query.getId() == null) {
            query.setId(UUIDUtils.getUUID());
            query.setStatus("1");
            ${lowerName}Mapper.insert(query);
        } else {
            ${lowerName}Mapper.updateByPrimaryKey(query);
        }
        rm.setMessage("保存成功！");
        return rm;
    }
    /**
    * 删除方法
    * @param dc 参数
    * @return ResponseMessage
    */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseMessage delData(DeleteCondition dc) {
        ResponseMessage rm = dc.validate();
        if (rm.hasError()) {
            return rm;
        }
        ${lowerName}Mapper.deleteByPrimaryKey(dc.getId());
        rm.setMessage("删除成功！");
        return rm;
    }
}
