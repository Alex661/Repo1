package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentBusiness stuBiz;

    /**
     * 获取所有学生信息
     */

    @GetMapping(value = "/student")
    public ResponseEntity<List<StudentDO>> list() {
        final List<StudentDO> students = stuBiz.getStudents();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * 根据学号查询学生信息
     *
     * @param stuNum 学生学号
     */
    @GetMapping(value = "/student/{stuNum}")
    public ResponseEntity<StudentDO> query(@PathVariable("stuNum") String stuNum) {
        Optional<StudentDO> stu = stuBiz.getStudent(stuNum);

        return stu.map(studentDO -> new ResponseEntity<>(studentDO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * 添加学生信息
     *
     * @param student 学生对象
     */
    @PostMapping(value = "/student")
    public ResponseEntity<Void> add(@RequestBody StudentDO student) {
        boolean result = stuBiz.saveStudent(student);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    /**
     * 更新学生信息
     *
     * @param student 学生对象
     */
    @PutMapping(value = "/student")
    public ResponseEntity<Void> save(@RequestBody StudentDO student) {
        boolean result = stuBiz.saveStudent(student);

        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    /**
     * 根据学号删除学生信息
     *
     * @param stuNum 学生学号
     */
    @RequestMapping(value = "/student/{stuNum}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("stuNum") String stuNum) {
        boolean result = stuBiz.deleteStudent(stuNum);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/student/verify")
    public ResponseEntity<Void> verifyStudent(@RequestBody StudentDO student){
        boolean result = stuBiz.verifyStudent(student);
        System.out.println(result);
        return new ResponseEntity<>(result? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
