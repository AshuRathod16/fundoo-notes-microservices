package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteDTO;
import com.bridgelabz.fundoonoteservice.exception.NoteException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.model.NoteModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 13-09-2022
 * Purpose: Creating method to send Email
 */

@Service
public class NoteService implements INoteService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    MailService mailService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LabelRepository labelRepository;

    @Override
    public NoteModel createNote(NoteDTO nodeDto, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            NoteModel model = new NoteModel(nodeDto);
            noteRepository.save(model);
            String body = "Note added successfully with noteId" + model.getNoteId();
            String subject = "Note created successfully";
            return model;
        }
        throw new NoteException(400, "Note not created ");
    }

    @Override
    public NoteModel updateNotes(NoteDTO noteDTO, Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTitle(noteDTO.getTitle());
                isNotePresent.get().setDescription(noteDTO.getDescription());
                isNotePresent.get().setUserId(noteDTO.getUserId());
                isNotePresent.get().setLabelId(noteDTO.getLabelId());
                isNotePresent.get().setColour(noteDTO.getColour());
                noteRepository.save(isNotePresent.get());
                String body = "Note updated successfully with note id" + isNotePresent.get().getNoteId();
                String subject = "Note updated successfully";
                mailService.send(isNotePresent.get().getEmailId(), subject, body);
                return isNotePresent.get();
            }
            throw new NoteException(400, "Note not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    @Override
    public List<NoteModel> readAllNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteModel> readAllNotes = noteRepository.findAll();
            if (readAllNotes.size() > 0) {
                return readAllNotes;
            }
            throw new NoteException(400, "Notes not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    @Override
    public Optional<NoteModel> readNotesById(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                return isNotePresent;
            }
            throw new NoteException(400, "Note not found");
        }
        throw new NoteException(400, "Token is invalid");
    }

    @Override
    public NoteModel archiveNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setArchive(true);
                noteRepository.save(isNotePresent.get());
                return isNotePresent.get();
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    @Override
    public NoteModel unArchiveNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setArchive(false);
                noteRepository.save(isNotePresent.get());
                return isNotePresent.get();
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    @Override
    public NoteModel restoreNote(long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setTrash(true);
                isIdPresent.get().setArchive(false);
                noteRepository.save(isIdPresent.get());
                return isIdPresent.get();
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }


    @Override
    public NoteModel pinNote(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setPin(true);
                noteRepository.save(isIdPresent.get());
                return isIdPresent.get();
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }

    @Override
    public NoteModel unPinNote(Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setPin(false);
                noteRepository.save(isIdPresent.get());
                return isIdPresent.get();
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }


    @Override
    public NoteModel changeNoteColor(Long noteId, String colour, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setColour(colour);
                noteRepository.save(isIdPresent.get());
                return isIdPresent.get();
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Token is wrong");
        }
    }

    @Override
    public NoteModel trashNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTrash(true);
                noteRepository.save(isNotePresent.get());
                return isNotePresent.get();
            }
            throw new NoteException(400, "Note with this id is not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    @Override
    public Response deleteNote(String token, Long noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isIdPresent = noteRepository.findById(noteId);
            if (isIdPresent.isPresent()) {
                return new Response(200, "Successfully", isIdPresent.get());
            }
            throw new NoteException(400, "User not found");
        }
        throw new NoteException(400, "Invalid token");
    }

    @Override
    public Response setReminder(Long noteId, LocalDateTime reminder, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()){
                if (!isNotePresent.get().isTrash()){
                    isNotePresent.get().setReminderTime(reminder);
                    noteRepository.save(isNotePresent.get());
                    return new Response(200, "Successfully", isNotePresent.get());
                } else {
                    throw new NoteException(400, "Note found in trash , unable to set reminder");
                }
            }
            throw new NoteException(400, "Note with this id not found");
        } else {
            throw new NoteException(400, "Invalid Token");
        }
    }

    @Override
    public Response addLabels(List<Long> labelId, Long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> isLabelPresent = new ArrayList<>();
            labelId.stream().forEach(label -> {
                Optional<LabelModel> isLabel = labelRepository.findById(label);
                if (isLabel.isPresent()) {
                    isLabelPresent.add(isLabel.get());
                }
            });
            Optional<NoteModel> note = noteRepository.findById(noteId);
            if (note.isPresent()) {
                note.get().setLabelId(isLabelPresent.toString());
                noteRepository.save(note.get());
                return new Response(200, "Successfully", note.get());
            }
        }
        return null;
    }

    @Override
    public Response addCollaborator(String emailId, Long noteId, List<String> collaborator) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/user/validate/" + emailId, Boolean.class);
        if (isUserPresent) {
            Optional<NoteModel> isNotePresent = noteRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().getEmailId();
                isNotePresent.get().setCollaborator(collaborator);
                noteRepository.save(isNotePresent.get());
                return new Response(200, "Successfully", isNotePresent.get());
            } else {
                throw new NoteException(400, "Note with this id is not found");
            }
        } else {
            throw new NoteException(400, "Inavalid Email Id");
        }
    }
}

