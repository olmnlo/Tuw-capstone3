# HAM group for physical therapy | Hussam
### my role here:
* Testing all endpoints and fix errors
* made the models and link relations and do validations
* made ControllerAdvice and its exceptions
* made Controller: `AnswerController`, `PatientController`, `VideoController`
* made Repository: `AnswerRepository`, `PatientRepository`, `VideoRepository`
* made Services: `AnswerService`, `PatientService`, `VideoService`, `VirusScanService`
* focous on video upload logic, virous check for mp4 videos

### extra I do:
* made DTOin: `AnswerDTO`, `DoctorDTO`, `PatientDTO`, `PlanDTO`, `QuestionDTO`, `ReportDTO`, `ScheduleDTO`, `VideoDTOin`
* made DTOout: `DoctorDTOout`, `VideoDTOout`
* made functions: `BookingService: takeFastestBooking, weeklyBookingNumbers, monthlyBookingNumbers`, `DoctorService: searchDoctorByNameContains`

### fixing I do in project:
* fix some DTOin: `BookingDTO`
* fixing BookingService: fix bean exception 'final' `(private 'final' repo ....)`
* fixing OpenAiConnect: fix `make it fetch also doctor check if he has pravlige to take this report`, `make patient must answer all quistions so I can generate AI report`, `made promt`, `make api retry to request 5 times`
* fixing PlanService: fix `using PlanDTO for update, add`
* fixing QuestionService: fix `using QuestionDTO update, add`
* fixing ReportPdfService: fix `pdf generator`
