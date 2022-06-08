package com.formssi.mall.goods.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.gms.cmd.GmsSpuSpecCmd;
import com.formssi.mall.gms.cmd.GmsSpuSpecValueCmd;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.dto.GmsSpuSpecValueDTO;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecValueDO;
import com.formssi.mall.goods.domain.repository.GmsSpuSpecRepository;
import com.formssi.mall.goods.domain.repository.GmsSpuSpecValueRepository;
import com.formssi.mall.goods.domain.repository.po.GmsSpuSpecAndValuePO;
import com.formssi.mall.goods.domain.service.GmsSpuSpecDomain;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSpuSpecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GmsSpuSpecDomainImpl implements GmsSpuSpecDomain {

    @Autowired
    private GmsSpuSpecRepository gmsSpuSpecRepository;

    @Autowired
    private GmsSpuSpecValueRepository gmsSpuSpecValueRepository;

    @Autowired
    private GmsSpuSpecMapper gmsSpuSpecMapper;

    /**
     * 保存数据到规格表和规格值表中
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void saveGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd) {
        GmsSpuSpecDO gmsSpuSpecDO = new GmsSpuSpecDO();
        BeanCopyUtils.copyProperties(gmsSpuSpecCmd, gmsSpuSpecDO);
        gmsSpuSpecRepository.save(gmsSpuSpecDO);
        gmsSpuSpecCmd.setSpecId(gmsSpuSpecDO.getId());
    }

    /**
     * 保存多条数据到规格值表中
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void saveGmsSpecValue(GmsSpuSpecCmd gmsSpuSpecCmd) {
        if (!CollectionUtils.isEmpty(gmsSpuSpecCmd.getSpecValueList())) {
            List<GmsSpuSpecValueDO> gmsSpuSpecValueDOList = gmsSpuSpecCmd.getSpecValueList().stream().map(gmsSpuSpecValueDTO -> {
                GmsSpuSpecValueDO gmsSpuSpecValueDO = new GmsSpuSpecValueDO();
                BeanCopyUtils.copyProperties(gmsSpuSpecValueDTO, gmsSpuSpecValueDO);
                gmsSpuSpecValueDO.setSpecId(gmsSpuSpecCmd.getSpecId());
                return gmsSpuSpecValueDO;
            }).collect(Collectors.toList());
            gmsSpuSpecValueRepository.saveBatch(gmsSpuSpecValueDOList);
        }
    }

    /**
     * 保存一条数据到规格值表中
     *
     * @param gmsSpuSpecValueCmd
     */
    @Override
    public void saveGmsSpecValue(GmsSpuSpecValueCmd gmsSpuSpecValueCmd) {
        GmsSpuSpecValueDO gmsSpuSpecValueDO = new GmsSpuSpecValueDO();
        BeanCopyUtils.copyProperties(gmsSpuSpecValueCmd,gmsSpuSpecValueDO);
        gmsSpuSpecValueRepository.save(gmsSpuSpecValueDO);
    }


    /**
     * 根据Id删除对应规格
     *
     * @param id
     */
    @Override
    public void deleteGmsSpec(Long id) {
        if (id != null) {
            gmsSpuSpecRepository.removeById(id);
        } else {
            throw new BusinessException("无效的删除规格！");
        }
    }

    /**
     * 根据规格id删除对应的规格值
     *
     * @param id
     */
    @Override
    public void deleteGmsSpecValue(Long id) {
        LambdaQueryWrapper<GmsSpuSpecValueDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GmsSpuSpecValueDO::getSpecId, id);
        gmsSpuSpecValueRepository.remove(wrapper);
    }

    /**
     * 修改规格表
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void updateGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd) {
        GmsSpuSpecDO gmsSpuSpecDO = new GmsSpuSpecDO();
        BeanCopyUtils.copyProperties(gmsSpuSpecCmd, gmsSpuSpecDO);
        if (gmsSpuSpecCmd.getSpecName() != null || gmsSpuSpecCmd.getStatus() != null) {
            gmsSpuSpecRepository.updateById(gmsSpuSpecDO);
        }
        gmsSpuSpecCmd.setSpecId(gmsSpuSpecDO.getId());
    }

    /**
     * 修改规格值表
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void updateGmsSpecValue(GmsSpuSpecCmd gmsSpuSpecCmd) {
        if (!CollectionUtils.isEmpty(gmsSpuSpecCmd.getSpecValueList())) {
            List<GmsSpuSpecValueDO> gmsSpuSpecValueDOList = gmsSpuSpecCmd.getSpecValueList().stream().map(gmsSpuSpecValueDTO -> {
                GmsSpuSpecValueDO gmsSpuSpecValueDO = new GmsSpuSpecValueDO();
                BeanCopyUtils.copyProperties(gmsSpuSpecValueDTO, gmsSpuSpecValueDO);
                return gmsSpuSpecValueDO;
            }).collect(Collectors.toList());
            gmsSpuSpecValueRepository.updateBatchById(gmsSpuSpecValueDOList);
        }
    }

    /**
     * 批量删除规格
     *
     * @param ids
     */
    @Override
    public void deleteGmsSpecBatch(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            gmsSpuSpecRepository.removeBatchByIds(ids);
        } else {
            throw new BusinessException("无效的批量删除！");
        }
    }

    /**
     * 根据规格id集合删除对应的规格值
     *
     * @param ids
     */
    @Override
    public void deleteGmsSpecValueBatch(List<Long> ids) {
        LambdaQueryWrapper<GmsSpuSpecValueDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(ids), GmsSpuSpecValueDO::getSpecId, ids);
        gmsSpuSpecValueRepository.remove(queryWrapper);
    }

    /**
     * 根据分类id查询对应的规格值
     *
     * @param catalogId
     */
    @Override
    public List<GmsSpuSpecDO> listGmsSpecByCategoryId(Long catalogId) {
        LambdaQueryWrapper<GmsSpuSpecDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GmsSpuSpecDO::getCatalogId, catalogId);
        return gmsSpuSpecRepository.list(queryWrapper);
    }

    /**
     * 根据条件查询规格和规格值
     *
     * @param gmsSpuSpecPageQry
     * @return
     */
    @Override
    public CommonPage<GmsSpuSpecDTO> listGmsSpecAndValuePage(GmsSpuSpecPageQry gmsSpuSpecPageQry) {
        long current = gmsSpuSpecPageQry.getCurrent();
        long page = (gmsSpuSpecPageQry.getCurrent() - 1) * gmsSpuSpecPageQry.getSize();
        gmsSpuSpecPageQry.setCurrent(page);
        List<GmsSpuSpecAndValuePO> gmsSpuSpecAndValuePOS = gmsSpuSpecMapper.listSpuSpecPage(gmsSpuSpecPageQry);
        ValidatorUtils.isCollectionEmpty(gmsSpuSpecAndValuePOS, "没有找到相关规格！");
        List<GmsSpuSpecDTO> gmsSpuSpecDTOList = new ArrayList<>();
        for (int i = 0, k = 0; i < gmsSpuSpecAndValuePOS.size(); i++) {
            GmsSpuSpecAndValuePO gmsSpuSpecAndValuePO = gmsSpuSpecAndValuePOS.get(i);
            GmsSpuSpecDTO gmsSpuSpecDTO;
            if (gmsSpuSpecDTOList.size() != 0 && gmsSpuSpecDTOList.get(k - 1).getId() == gmsSpuSpecAndValuePO.getId()) {
                gmsSpuSpecDTO = gmsSpuSpecDTOList.get(k - 1);
                GmsSpuSpecValueDTO gmsSpuSpecValueDTO = gmsSpuSpecAndValuePO.getGmsSpuSpecValueDTO();
                gmsSpuSpecDTO.getSpecValueList().add(gmsSpuSpecValueDTO);
            } else {
                List<GmsSpuSpecValueDTO> gmsSpuSpecValueDTOList = new ArrayList<>();
                gmsSpuSpecDTO = new GmsSpuSpecDTO();
                BeanCopyUtils.copyProperties(gmsSpuSpecAndValuePO, gmsSpuSpecDTO);
                GmsSpuSpecValueDTO gmsSpuSpecValueDTO = gmsSpuSpecAndValuePO.getGmsSpuSpecValueDTO();
                gmsSpuSpecValueDTOList.add(gmsSpuSpecValueDTO);
                gmsSpuSpecDTO.setSpecValueList(gmsSpuSpecValueDTOList);
                gmsSpuSpecDTOList.add(gmsSpuSpecDTO);
                k++;
            }
        }
        Long total = gmsSpuSpecMapper.getCount();
        return new CommonPage<>(current, gmsSpuSpecPageQry.getSize(), total, gmsSpuSpecDTOList);
    }

}
