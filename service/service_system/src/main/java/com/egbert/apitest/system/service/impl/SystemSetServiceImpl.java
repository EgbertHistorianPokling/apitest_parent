package com.egbert.apitest.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egbert.apitest.model.sys.SystemSet;
import com.egbert.apitest.system.mapper.SystemSetMapper;
import com.egbert.apitest.system.service.SystemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemSetServiceImpl extends ServiceImpl<SystemSetMapper, SystemSet> implements SystemSetService {

}
