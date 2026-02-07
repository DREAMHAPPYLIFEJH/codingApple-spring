package com.jhcompany.demo.comment;

import com.jhcompany.demo.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// list.html에서 /list url이랑 /list/page/{num} url이 꼬이는듯
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Long parentId,
                       Authentication auth
    ) {
        System.out.println("error");
        CustomUser user = (CustomUser) auth.getPrincipal();
        var data = new Comment();
        data.setContent(content);
        data.setUsername(user.getUsername());
        data.setParentId(parentId);
        commentService.saveComment(data); // 이게 작동이 안됨
        return "redirect:/detail/" + parentId;
    }
}
