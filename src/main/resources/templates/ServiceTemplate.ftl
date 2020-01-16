package ${bussPackage}.service;

import javax.annotation.Resource;
import ${bussPackage}.vo.${className}VO;
import ${bussPackage}.mapper.${className}Mapper;
import ${bussPackage}.pojo.DeleteCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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
    * 获取所以数据
    *
    * @return List
    */
    public List<${className}PO> getListData() {
        ${className}VO query = new ${className}VO();
        query.setRows(0);
        return ${lowerName}Mapper.pageQuery(query);
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
    * Excel导入方法
    *
    * @param excelFile 参数
    * @return ResponseMessage
    */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseMessage batchSaveData(MultipartFile excelFile) throws IOException {
        ResponseMessage rm = ResponseMessage.getInstance();
        DataListener<${className}VO> dataListener = new DataListener<>();
        ExcelReader excelReader = EasyExcel.read(excelFile.getInputStream(), ${className}VO.class, dataListener).headRowNumber(1).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();

        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        List<${className}VO> list = dataListener.getList();
            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                ${className}VO object = list.get(i);
                if (false) {
                    rm.setErrorMsg("第" + (i + 2) + "行：" + "不能为空！");
                    return rm;
                }
            }
            ${lowerName}Mapper.batchInsert(list);
            } else {
            rm.setErrorMsg("导入文件无数据！");
            return rm;
            }
            rm.setMessage("导入成功！");
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
        ${lowerName}Mapper.deleteByIds(dc);
        rm.setMessage("删除成功！");
        return rm;
    }
}
