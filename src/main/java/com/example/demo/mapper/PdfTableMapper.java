package com.example.demo.mapper;

import com.example.demo.pojo.PdfTablePO;
import com.example.demo.vo.PdfTableVO;

import java.util.List;

/**
 * 
 * pdf单元格属性 接口
 * @author yk
 * 
 */
public interface PdfTableMapper {

   int deleteByPrimaryKey(String id);

   int insert(PdfTableVO record);

   int insertSelective(PdfTableVO record);

   PdfTablePO selectByPrimaryKey(String id);

   int updateByPrimaryKeySelective(PdfTableVO record);

   int updateByPrimaryKey(PdfTableVO record);

   List<PdfTablePO> pageQuery(PdfTableVO record);

   List<PdfTablePO> selectById(String id);

   long getRowNumber(PdfTableVO record);
}