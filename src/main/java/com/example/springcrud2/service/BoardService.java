package com.example.springcrud2.service;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.BoardRequestDto;
import com.example.springcrud2.dto.BoardResponseDto;
import com.example.springcrud2.dto.ResponseDto;
import com.example.springcrud2.entity.Board;
import com.example.springcrud2.entity.Liked;
import com.example.springcrud2.entity.LikeBoard;
import com.example.springcrud2.entity.Member;
import com.example.springcrud2.repository.BoardRepository;

import com.example.springcrud2.repository.LikeBoardRepository;
import com.example.springcrud2.repository.LikedRepository;
import com.example.springcrud2.repository.MemberRepository;
import com.example.springcrud2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor // 이거는 또 뭘까
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final LikedRepository likedRepository;

    @Transactional // 이거를 빼면 데이터 생성이 안되나???? //HttpServletRequest request
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, UserDetailsImpl userDetailsImpl) {
        Board board = new Board(boardRequestDto, userDetailsImpl.getUser());
        boardRepository.save(board);
        List<Liked> likeds = likedRepository.findAllByBoard(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board, Long.valueOf(likeds.size()));
        return boardResponseDto;
    }

    @Transactional // List<Board> -> List로 바꿔서 리턴해보기
    public List<BoardResponseDto> readBoard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtAsc(); // 복수형
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boards) {
            List<Liked> likeds = likedRepository.findAllByBoard(board);
            BoardResponseDto tempboardResponseDto = new BoardResponseDto(board, Long.valueOf(likeds.size()));
            boardResponseDto.add(tempboardResponseDto);
        }
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, UserDetailsImpl userDetailsImpl) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.get().getMember().getId().equals(userDetailsImpl.getUser().getId()) && !userDetailsImpl.getUser().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        board.get().BoardUpdate(boardRequestDto);
        List<Liked> likeds = likedRepository.findAllByBoard(board.get());
        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get(), Long.valueOf(likeds.size()));
        return boardResponseDto;
    }

    public ResponseDto deleteBoard(Long id, UserDetailsImpl userDetailsImpl) {
        //Map deleteMessage = new HashMap<Integer, Integer>();
        Optional<Board> board = boardRepository.findById(id);
        if (!board.get().getMember().getId().equals(userDetailsImpl.getUser().getId()) && !userDetailsImpl.getUser().getRole().equals(MemberEnum.ADMIN)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        boardRepository.delete(board.get());
        return new ResponseDto(HttpStatus.OK.value(), "게시글 삭제 성공");
    }

    //@Transactional // List<Board> -> List로 바꿔서 리턴해보기 // Transactional은 수정사항에?
    public BoardResponseDto detailBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지않음")
        );
        List<Liked> likeds = likedRepository.findAllByBoard(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board, Long.valueOf(likeds.size()));
        return boardResponseDto;
    }
    @Transactional
    public void likeBoard(@PathVariable Long id, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지않음")
        );

        if (likedRepository.findByMemberAndBoard(member, board).isPresent()) {
            Liked liked = likedRepository.findByMemberAndBoard(member, board).orElseThrow(
                    () -> new IllegalArgumentException("좋아요가 존재하지않음")
            );
            likedRepository.delete(liked);
        } else {
            Liked liked = new Liked(member, board);
            likedRepository.save(liked);
        }


//        List<Member> memberLikes = board.getMemberLikes();
//        if (memberLikes.isEmpty()) {
//            board.BoardLikeAdd(member, board);
//        } else {
//            Iterator<Member> m = memberLikes.listIterator();
//            while (m.hasNext()) {
//                Member likeMember = m.next();
//                if (likeMember.getId() == member.getId()) {
//                    m.remove();
//                } else {
//                    board.BoardLikeAdd(member, board);
//                }
//            }
//        }

//        if (likeBoardRepository.findByMemberAndBoard(member, board).isPresent()) {
//            LikeBoard likeBoard = likeBoardRepository.findByMemberAndBoard(member, board).orElseThrow(
//                    () -> new IllegalArgumentException("좋아요가 존재하지않음")
//            );
//            likeBoardRepository.delete(likeBoard);
//        } else {
//            LikeBoard likeBoard = new LikeBoard(member, board);
//            likeBoardRepository.save(likeBoard);
//        }
    }
}
