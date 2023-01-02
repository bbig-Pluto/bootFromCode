package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." +i).build();
            memoRepository.save(memo);
        });
    }


    @Test
    public void testSelect() {

        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        }

    }

//    @Test
//    @Transactional
//    public void testSelect2() {
//
//        Long mno = 100L;
//
//        Memo memo = memoRepository.getOne(mno);
//
//        System.out.println("===============================");
//
//        System.out.println("memo = " + memo);
//    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("Update Text3").build();

        System.out.println(memoRepository.save(memo));
    }

    // p.63
    @Test
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    // p.68
    @Test
    public void testPageDefault() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("result = " + result);

        System.out.println("===============================================");

        // 총 몇 페이지
        System.out.println("Total Pages : " + result.getTotalPages());

        // 전체 개수
        System.out.println("Total Counts : " + result.getTotalElements());

        // 현제 페이지 번호 0 부터 시작
        System.out.println("Page Number : " + result.getNumber());

        // 페이지당 데이터 개수
        System.out.println("Page Size : " + result.getSize());

        // 다음 페이지 존재 여부
        System.out.println("has next Page? : " + result.hasNext());

        // 시작 페이지(0) 여부
        System.out.println("first Page? : " + result.isFirst());

        System.out.println("-----------------------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    // p.69
    @Test
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("memo = " + memo);
        });
    }

}
