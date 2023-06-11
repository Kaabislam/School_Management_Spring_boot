package com.kaab.service;

import com.kaab.dao.RequestDao;
import com.kaab.entity.Request;
import com.kaab.entity.RequestStatus;
import com.kaab.entity.Student;
import com.kaab.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    @Autowired
    private final RequestDao requestDao;

    @Autowired
    public RequestService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public Request createRequest(Student student, Teacher teacher) {
        Request request = new Request();
        request.setStudent(student);
        request.setTeacher(teacher);
        request.setStatus(RequestStatus.PENDING);
        return requestDao.save(request);
    }


//    public List<Request> getRequestsByStudent(Student student) {
//        return requestRepository.findByStudent(student);
//    }
//
//    public List<Request> getRequestsByTeacher(Teacher teacher) {
//        return requestRepository.findByTeacher(teacher);
//    }
//
//    public Request updateRequestStatus(Request request, RequestStatus status) {
//        request.setStatus(status);
//        return requestRepository.save(request);
//    }
}
