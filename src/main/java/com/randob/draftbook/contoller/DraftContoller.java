package com.randob.draftbook.contoller;

import com.randob.draftbook.exception.ResourceNotFoundException;
import com.randob.draftbook.model.Draft;
import com.randob.draftbook.repository.DraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DraftContoller {

    @Autowired
    private DraftRepository draftRepository;

    //get all drafts
    @GetMapping("draft")
    public List<Draft> getAllDrafts(){
        return this.draftRepository.findAll();
    }

    //get draft by id
    @GetMapping("draft/{id}")
    public ResponseEntity<Draft> getDraftById(@PathVariable(value = "id") Long draftId) throws ResourceNotFoundException {
        Draft draft = this.draftRepository.findById(draftId).orElseThrow(() -> new ResourceNotFoundException("Draft not found id: " + draftId));
        return ResponseEntity.ok().body(draft);
    }

    //create draft
    @PostMapping("draft")
    public Draft createDraft(@RequestBody Draft draft){
        return this.draftRepository.save(draft);
    }

    //update draft
    @PutMapping("draft/{id}")
    public ResponseEntity<Draft> updateDraft(@PathVariable(value = "id") Long draftId, @RequestBody Draft draftBody) throws ResourceNotFoundException {
        Draft draft = this.draftRepository.findById(draftId).orElseThrow(() -> new ResourceNotFoundException("Draft not found id: " + draftId));

        draft.setTitle(draftBody.getTitle());
        draft.setText(draftBody.getText());

        return ResponseEntity.ok(this.draftRepository.save(draft));
    }

    //delete draft
    @DeleteMapping("draft/{id}")
    public Map<String, Boolean> deleteDraft(@PathVariable(value = "id") Long draftId) throws ResourceNotFoundException {
        Draft draft = this.draftRepository.findById(draftId).orElseThrow(() -> new ResourceNotFoundException("Draft not found id: " + draftId));

        this.draftRepository.deleteById(draftId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
