package com.example.huiben3;


import android.os.Environment;

import com.example.huiben3.entity.Book;
import com.example.huiben3.entity.Page;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FolderScanService {

    //    @Value("${book.base.path}")
    private String basePath = Environment.getExternalStorageDirectory() + "/data/book/"; // /storage/emulated/0

    // /storage/emulated/0/data/book
    // 获取图书列表
    public List<Book> scan() {
        print("根目录" + basePath);
        List<Book> books = new ArrayList();

        File base = new File(basePath);
        if (!base.exists()) {
            return books;
        }
        print("根目录" + base.getAbsolutePath());
        File[] paths = base.listFiles();
        print("paths=" + paths);
        if (paths == null || paths.length == 0) {
            print("没有绘本");
            return books;
        }
        for (File p : paths) {
            Book book = new Book();
            book.setName(p.getName());
            book.setPath(p.getAbsolutePath());
            books.add(book);
        }
        Comparator comparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };
        books.sort(comparator);
        return books;
    }

    // 根据路径获取图书详细内容
    public Book getBook(Book book) {
        List<Page> pageList = new ArrayList();
        File[] pageFileList = new File(book.getPath()).listFiles();
        if (pageFileList.length > 0) {
            for (File f : pageFileList) {
                Page page = new Page();
                page.setName(f.getName());
                page.setSize(f.length());
                page.setPath(f.getAbsolutePath());
                pageList.add(page);
            }
        }
        // 按名称排序
        Comparator comparator = new Comparator<Page>() {
            @Override
            public int compare(Page o1, Page o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };
        pageList.sort(comparator);

        book.setPageList(pageList);

        return book;
    }

    private void print(String s) {
        System.out.println(s);
    }

}
