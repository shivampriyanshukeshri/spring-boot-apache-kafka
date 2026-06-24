package com.glory.global.runner;

import com.glory.global.entity.Book;
import com.glory.global.entity.Student;
import com.glory.global.repo.BookRepo;
import com.glory.global.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner{
    @Autowired private StudentRepo studentRepo;
    @Autowired private BookRepo bookRepo;

    @Override
    public void run(String... args) throws Exception{
        Student student = new Student();
        student.setName("Priyanshu Shivam");
        student.setMobileNo(7070043891L);

        if(this.studentRepo.findByMobileNo(7070043891L).isEmpty())
            this.studentRepo.save(student);

        Book b1 = new Book();
        b1.setTitle("Concepts of Physics");
        b1.setBooksLeft(5);

        Book b2 = new Book();
        b2.setTitle("Introduction to Algorithms");
        b2.setBooksLeft(5);

        if(this.bookRepo.findByTitle("Concepts of Physics").isEmpty())
            this.bookRepo.save(b1);

        if(this.bookRepo.findByTitle("Introduction to Algorithms").isEmpty())
            this.bookRepo.save(b2);
    }
}
