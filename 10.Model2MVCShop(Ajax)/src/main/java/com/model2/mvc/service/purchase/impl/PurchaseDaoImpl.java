package com.model2.mvc.service.purchase.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;

    @Override
    public void addPurchase(Purchase purchase) throws Exception {
        sqlSession.insert("PurchaseMapper.addPurchase", purchase);
    }

    @Override
    public Purchase getPurchase(int tranNo) throws Exception {
        return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
    }

    @Override
    public void updatePurchase(Purchase purchase) throws Exception {
        sqlSession.update("PurchaseMapper.updatePurchase", purchase);
    }

    @Override
    public void updateTranCode(Purchase purchase) throws Exception {
        sqlSession.update("PurchaseMapper.updateTranCode", purchase);
    }

    @Override
    public List<Purchase> getPurchaseList(Search search) throws Exception {
        return sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
    }

    @Override
    public List<Purchase> getSaleList(Search search) throws Exception {
        return sqlSession.selectList("PurchaseMapper.getSaleList", search);
    }

    @Override
    public int getTotalCount(Search search) throws Exception {
        return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
    }
}
