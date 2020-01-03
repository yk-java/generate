package com.example.demo.mapper;

import ${bussPackage}.pojo.${className}PO;
import ${bussPackage}.vo.${className}VO;

import java.util.List;

/**
 * 
 * ${codeName} 接口
 * @author ${developer}
 * 
 */
public interface ${className}Mapper {

   int deleteByPrimaryKey(String id);

   int insert(${className}VO record);

   int insertSelective(${className}VO record);

   void batchInsert(List<${className}VO> list);

   ${className}PO selectByPrimaryKey(String id);

   int updateByPrimaryKeySelective(${className}VO record);

   int updateByPrimaryKey(${className}VO record);

   List<${className}PO> pageQuery(${className}VO record);

   List<${className}PO> selectById(String id);

   long getRowNumber(${className}VO record);
}