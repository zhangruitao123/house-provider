package cn.house.houseprovider.controller;

import cn.house.houseprovider.pojo.Result;
import cn.house.houseprovider.pojo.User;
import cn.house.houseprovider.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExcelController {


    @Autowired
    UserService userService;

    /**
     * 导入Excel，添加用户
     *  文件上传：springboot
     */
    @RequestMapping(value="/import",method = RequestMethod.POST)
    public Result importUser(@RequestParam(name="file") MultipartFile file) throws Exception {
        //1.解析Excel
        //1.1.根据Excel文件创建工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //1.2.获取Sheet
        Sheet sheet = wb.getSheetAt(0);//参数：索引
        //1.3.获取Sheet中的每一行，和每一个单元格
        //2.获取用户数据列表
        List<User> list = new ArrayList<>();
        System.out.println(sheet.getLastRowNum());
        for (int rowNum = 2; rowNum<= sheet.getLastRowNum() ;rowNum ++) {
            Row row = sheet.getRow(rowNum);//根据索引获取每一个行
            Object [] values = new Object[row.getLastCellNum()];
            for(int cellNum=0;cellNum< row.getLastCellNum(); cellNum ++) {
                Cell cell = row.getCell(cellNum);
                Object value = getCellValue(cell);
                values[cellNum] = value;
            }
            //在User的构造函数中，根据表格值顺序设置User对应的属性值
            User user = new User(values);
            list.add(user);
        }
        //3.调用服务层批量保存用户
        System.out.println("======="+list.toString()+"=========");
        userService.saveAll(list);

        return new Result("SUCCESS");
    }

    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }





}
