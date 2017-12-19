.open anki_aniki_free_2017_01_13-22_44_48.db

select Words.kanji 
from QuizQuestions 
join Words 
on Words.id = QuizQuestions.word_id 
join QuizAnswers 
on QuizAnswers.question_number = QuizQuestions.question_number 
where QuizAnswers.isCorrect=="Yes" and QuizAnswers.isSelected=="No" 
group by Words.kanji 
order by COUNT(QuizAnswers.question_number) DESC 
limit 40;

select kanji 
from words 
where times_reading_incorrect>3 or times_kanji_incorrect>3 or times_meaning_incorrect>3;