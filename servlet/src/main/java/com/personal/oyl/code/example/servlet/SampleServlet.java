package com.personal.oyl.code.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleServlet extends HttpServlet {
    private static final long serialVersionUID = -2476530602619285503L;
    
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(100), Executors.defaultThreadFactory(), new AbortPolicy());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间：" + new Date() + ".<br/>");
        out.flush();

        //开始异步处理，由另一个线程池负责业务处理
        AsyncContext ctx = req.startAsync();
        pool.submit(new Executor(ctx));
        
        out.println("主线程退出Servlet的时间：" + new Date() + ".<br/>");
        out.flush();
    }

    static class Executor implements Runnable {
        private AsyncContext ctx = null;
        public Executor(AsyncContext ctx){
            this.ctx = ctx;
        }
     
        public void run(){
            try {
                Thread.sleep(1000);
                PrintWriter out = ctx.getResponse().getWriter();
                out.println("业务处理完毕的时间：" + new Date() + ".<br/>");
                out.flush();
                ctx.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
