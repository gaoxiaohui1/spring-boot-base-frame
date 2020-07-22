package com.base.frame.api.controller;

import com.base.frame.api.vo.ExcelVO;
import com.base.frame.common.tools.mail.MailService;
import com.base.frame.common.utils.ExcelUtil;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelController {

    /**
     * 邮件发送
     */
    @Autowired
    private MailService mailService;

    /**
     * 报警邮件收信人
     */
    @Value("${spring.mail.to}")
    private String mailTo;

    /**
     * 发送excel附件
     *
     * @return
     */
    @ApiOperation(value = "发送excel附件", notes = "发送excel附件")
    @RequestMapping(path = "sendExcel", method = RequestMethod.GET)
    public BaseResult sendExcel() {
        String fileName = "统计数据导出";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("统计数据导出");
        List<ExcelVO> dataList = new ArrayList<>();
        ExcelVO excelVO = new ExcelVO("北京", 12);
        dataList.add(excelVO);
        HSSFWorkbook excel = ExcelUtil.generateExcel(fileName, dataList);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excel.write(byteArrayOutputStream);
            byte[] bt = byteArrayOutputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bt, 0, bt.length);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            DataSource dataSource = new ByteArrayDataSource(inputStream, "application/msexcel");
            mailService.sendAttachmentsMail(mailTo.split(","), stringBuilder.toString(), stringBuilder.toString(), fileName + ".xls", dataSource);
        } catch (Exception e) {

        }
        return ResultGenerator.success();
    }
}
