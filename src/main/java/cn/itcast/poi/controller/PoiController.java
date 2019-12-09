package cn.itcast.poi.controller;

import cn.itcast.poi.pojo.CoursePlan;
import cn.itcast.poi.service.CoursePlanService;
import cn.itcast.poi.utils.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class PoiController {

    @Autowired
    private CoursePlanService service;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public List<CoursePlan> getAll() throws IOException, ParseException {
        //service.exportExcel();
        return service.importExcel();
    }

    @RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/down",method = RequestMethod.GET)
    public void down(String fileName, HttpServletRequest request,
                     HttpServletResponse response) throws Exception {
        List<CoursePlan> list = service.getAll();
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
        //5.创建文件名
        fileName=new String(fileName.getBytes("iso8859-1"),"utf-8");
        //6.获取输出流对象
        ServletOutputStream outputStream = response.getOutputStream();
        //7.获取浏览器信息,对文件名进行重新编码
        fileName = FileUtils.filenameEncoding(fileName, request);
        //8.设置信息头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //9.写出文件,关闭流
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
    }

    @RequestMapping(value = "/downJpg",method = RequestMethod.GET)
    public void jpg(String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception {
        fileName=new String(fileName.getBytes("iso8859-1"),"utf-8");
        InputStream fis=new FileInputStream("C:/Users/pf/Desktop/cs/01.jpg");
        ServletOutputStream outputStream = response.getOutputStream();
        //获取浏览器信息,对文件名进行重新编码
        fileName= FileUtils.filenameEncoding(fileName, request);
        //设置信息头
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        byte[] byt=new byte[2048];
        int len=0;
        while((len=fis.read(byt))>0){
            outputStream.write(byt,0,len);
        }
        outputStream.flush();
        fis.close();
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public void upload(@RequestParam("file")MultipartFile multipartFile,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        if (multipartFile==null||multipartFile.getSize()<=0){
            response.getWriter().println("上传文件不能为空");
            return;
        }
        String picName= UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        //获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //获取文件后缀名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //图片上传的路径
        String path="C:/Users/pf/Desktop/cs/"+picName+ext;
        //开始上传
        multipartFile.transferTo(new File(path));
        response.getWriter().println("上传成功");
        return;
    }
}
