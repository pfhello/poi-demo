package cn.itcast.poi.service;

import cn.itcast.poi.mapper.CoursePlanMapper;
import cn.itcast.poi.pojo.CoursePlan;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CoursePlanService {

    @Autowired
    private CoursePlanMapper mapper;

    @Transactional
    public List<CoursePlan> getAll(){
        return mapper.getAll();
    }

    //创建Excel
    public void exportExcel() throws IOException {
        List<CoursePlan> list = mapper.getAll();
        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作表对象
        HSSFSheet sheet = hssfWorkbook.createSheet();
        //3.创建标题行
        HSSFRow title = sheet.createRow(0);
        title.createCell(0).setCellValue("课程名称");
        title.createCell(1).setCellValue("学号");
        title.createCell(2).setCellValue("姓名");
        title.createCell(3).setCellValue("班级");
        title.createCell(4).setCellValue("日期");
        title.createCell(5).setCellValue("地点");
        //4.遍历数据创建数据行
        for (CoursePlan coursePlan:list){
            //获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            if (coursePlan.getCourse()!=null)
            dataRow.createCell(0).setCellValue(coursePlan.getCourse());
            if (coursePlan.getStudentNumber()!=null)
            dataRow.createCell(1).setCellValue(coursePlan.getStudentNumber().intValue());
            if (coursePlan.getName()!=null)
            dataRow.createCell(2).setCellValue(coursePlan.getName());
            if (coursePlan.getStudentClass()!=null)
            dataRow.createCell(3).setCellValue(coursePlan.getStudentClass());
            dataRow.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (coursePlan.getTestPlace()!=null)
            dataRow.createCell(5).setCellValue(coursePlan.getTestPlace());
        }

        //5.创建输出流
        FileOutputStream fos=new FileOutputStream("C:\\Users\\pf\\Desktop\\cs\\考试安排表.xls");
        //6.写出文件,关闭流
        hssfWorkbook.write(fos);
        hssfWorkbook.close();
        fos.close();
    }

    //读取Excel
    public List<CoursePlan> importExcel() throws IOException, ParseException {
        List<CoursePlan> list=new ArrayList<>();
        //1.获取文件输入流
        FileInputStream fis=new FileInputStream("C:\\Users\\pf\\Desktop\\cs\\考试安排表.xls");
        //2.获取Excel工作簿对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
        //3.获取工作表对象
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //4.循环读取表格中的数据
        for (Row row:sheet){
            if (row.getRowNum()==0){
                continue;
            }
            CoursePlan coursePlan=new CoursePlan();
            if (row.getCell(0)!=null)
            coursePlan.setCourse(row.getCell(0).getStringCellValue());
            if (row.getCell(1)!=null)
            coursePlan.setStudentNumber((long) row.getCell(1).getNumericCellValue());
            if (row.getCell(2)!=null)
            coursePlan.setName(row.getCell(2).getStringCellValue());
            if (row.getCell(3)!=null)
            coursePlan.setStudentClass(row.getCell(3).getStringCellValue());
            if (row.getCell(4)!=null)
            coursePlan.setTestTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.getCell(4).getStringCellValue()));
            if (row.getCell(5)!=null)
            coursePlan.setTestPlace(row.getCell(5).getStringCellValue());
            list.add(coursePlan);
        }
        hssfWorkbook.close();
        fis.close();
        return list;
    }
}
