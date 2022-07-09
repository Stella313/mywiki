package com.wiki.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiki.mywiki.domain.Category;
import com.wiki.mywiki.domain.CategoryExample;
import com.wiki.mywiki.mapper.CategoryMapper;
import com.wiki.mywiki.req.CategoryQueryReq;
import com.wiki.mywiki.req.CategorySaveReq;
import com.wiki.mywiki.resp.CategoryQueryResp;
import com.wiki.mywiki.resp.PageResp;
import com.wiki.mywiki.util.CopyUtil;
import com.wiki.mywiki.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CategoryMapper categoryMapper;
    private UuidUtils uuidUtils;
    public List<Category> list(){
        return categoryMapper.selectByExample(null);
    }

    public List<CategoryQueryResp> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList =  categoryMapper.selectByExample(categoryExample);
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return list;
    }

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        // 只对下面遇到的第一个sql起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList =  categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(CategorySaveReq req){
        Category category = CopyUtil.copy(req, Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            // 新增
            category.setId(uuidUtils.getId());
            categoryMapper.insert(category);
        }else{
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
