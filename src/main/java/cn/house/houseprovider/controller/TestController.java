package cn.house.houseprovider.controller;

import cn.house.houseprovider.pojo.User;
import cn.house.houseprovider.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception {
        //1.构造数据,某个月的用户数据
        List<User> list =userService.findAll();
        //2.创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //3.构造sheet
        String[] titles = {"编号", "姓名", "手机"};
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        AtomicInteger headersAi = new AtomicInteger();
        for (String title : titles) {
            Cell cell = row.createCell(headersAi.getAndIncrement());
            cell.setCellValue(title);
        }
        AtomicInteger datasAi = new AtomicInteger(1);
        Cell cell = null;
        for (User user : list) {
            Row dataRow = sheet.createRow(datasAi.getAndIncrement());
            //编号
            cell = dataRow.createCell(0);
            cell.setCellValue(user.getUserId());
            //姓名
            cell = dataRow.createCell(1);
            cell.setCellValue(user.getUserName());
            //手机
            cell = dataRow.createCell(2);
            cell.setCellValue(user.getMobile());
            //最高学历
//            cell = dataRow.createCell(3);
//            cell.setCellValue(user.getTheHighestDegreeOfEducation());
            //国家地区
//            cell = dataRow.createCell(4);
//            cell.setCellValue(user.getNationalArea());
//            //生日
//            cell = dataRow.createCell(7);
//            cell.setCellValue(user.getBirthday());
            //时间
//            cell = dataRow.createCell(12);
//            cell.setCellValue(user.getCreateTime());
        }
        String fileName = URLEncoder.encode("人员信息.xlsx", "UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
        response.setHeader("filename", fileName);
        FileOutputStream pis = new FileOutputStream("E:\\test2.xlsx");

        workbook.write(pis);
        pis.close();

        System.out.println("这是新加的内容！！！！！！");
    }





}
