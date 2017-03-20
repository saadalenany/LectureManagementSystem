--
-- Database: `lecturedb`
--
CREATE DATABASE IF NOT EXISTS `lecturedb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `lecturedb`;

-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE `absence` (
  `student_id` varchar(20) NOT NULL,
  `lecture_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`student_id`, `lecture_id`) VALUES
('121', 1111111),
('121', 1111112),
('4655', 1111112),
('545', 1111111),
('54121', 1111112),
('54121', 1111118),
('121', 1111118),
('5412', 1111119),
('54121', 1111120),
('54121', 1111121),
('121', 1111121),
('5412', 1111121),
('121', 1111122),
('54121', 1111122),
('545', 1111122),
('158544', 1111129),
('158544', 1111133);

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `doctor_id` varchar(20) NOT NULL,
  `doctor_course` varchar(60) NOT NULL,
  `numberofquizes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`doctor_id`, `doctor_course`, `numberofquizes`) VALUES
('123', '12', 0),
('123456', 'hhg', 0);

-- --------------------------------------------------------

--
-- Table structure for table `lecture`
--

CREATE TABLE `lecture` (
  `lecture_id` int(11) NOT NULL,
  `lecture_name` varchar(60) NOT NULL,
  `doctor_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lecture`
--

INSERT INTO `lecture` (`lecture_id`, `lecture_name`, `doctor_id`) VALUES
(1111111, 'lec1', '123'),
(1111112, 'lec2', '123'),
(1111118, 'lec3', '123'),
(1111119, 'lec4', '123'),
(1111120, 'lec5', '123'),
(1111121, 'lec6', '123'),
(1111122, 'lec7', '123'),
(1111123, 'lec8', '123'),
(1111124, 'lec9', '123'),
(1111125, 'lec10', '123'),
(1111126, 'lec11', '123'),
(1111127, 'lec12', '123'),
(1111128, 'lec13', '123'),
(1111129, 'lec14', '123'),
(1111130, 'lec15', '123'),
(1111131, 'lec16', '123'),
(1111132, 'lec17', '123'),
(1111133, 'lec15', '123');

-- --------------------------------------------------------

--
-- Table structure for table `note`
--

CREATE TABLE `note` (
  `note_id` int(11) NOT NULL,
  `note_data` varchar(1000) NOT NULL,
  `note_slide` int(11) NOT NULL,
  `note_ownerid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `question_id` int(11) NOT NULL,
  `question_data` varchar(500) NOT NULL,
  `questionownerid` varchar(20) NOT NULL,
  `reply` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quiz_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quiz_id`, `lecture_id`) VALUES
(1, 1111111),
(2, 1111112),
(3, 1111119),
(4, 1111121),
(5, 1111122),
(6, 1111123),
(7, 1111124),
(8, 1111125),
(9, 1111127),
(10, 1111129),
(11, 1111129);

-- --------------------------------------------------------

--
-- Table structure for table `quizquestion`
--

CREATE TABLE `quizquestion` (
  `quizquestion_id` int(11) NOT NULL,
  `numberofchoices` int(11) NOT NULL,
  `question_data` varchar(200) NOT NULL,
  `choice_data` varchar(500) NOT NULL,
  `doctor_ans` varchar(300) NOT NULL,
  `quiz_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quizquestion`
--

INSERT INTO `quizquestion` (`quizquestion_id`, `numberofchoices`, `question_data`, `choice_data`, `doctor_ans`, `quiz_id`) VALUES
(1, 2, 'bla bla bla', 'blll,jjj', '1', 1),
(2, 2, 'hhhhhhhhhhjjhi', 'jjj,kkk', '1', 1),
(3, 3, 'huyguhopl[', 'kk,ll,oo', '1', 2),
(4, 3, 'ryjtdeuhreaih', 'kk,rr,uu', '1', 2),
(5, 3, 'efyyyyyyyyygapugi', 'iik,wasfrm,dsfg', '1', 2),
(6, 3, 'trfgyhnjnuhygtfrdeftgybhunj', 'hhh,jjhh,jiujhi,', '2', 3),
(7, 3, 'uygtfyuhjm', 'uhiogyigtu,yugitufry,gtyghui;,', '1', 3),
(8, 3, 'rftgyhbunjkmo', 'tyghuj,nujhyg,hjko,', '3', 3),
(9, 3, 'rftgyhunjimkomjuhy', 'hyui,ijuhy,jhg,', '1', 4),
(10, 3, 'tgyhunjimkol', 'juhygt,jhgf,mjhg,', '1', 4),
(11, 3, 'fghnjmkjh', 'jhghjk,yhujniou,hgfkjnhbkj,', '1', 4),
(12, 3, 'rftgyhunjimkomjuhy', 'hyui,ijuhy,jhg,', '1', 5),
(13, 3, 'tgyhunjimkol', 'juhygt,jhgf,mjhg,', '1', 5),
(14, 3, 'fghnjmkjh', 'jhghjk,yhujniou,hgfkjnhbkj,', '1', 5),
(15, 3, 'rftgyhunjimkomjuhy', 'hyui,ijuhy,jhg,', '1', 6),
(16, 3, 'tgyhunjimkol', 'juhygt,jhgf,mjhg,', '1', 6),
(17, 3, 'fghnjmkjh', 'jhghjk,yhujniou,hgfkjnhbkj,', '1', 6),
(18, 3, 'rftgyhunjimkomjuhy', 'hyui,ijuhy,jhg,', '1', 7),
(19, 3, 'tgyhunjimkol', 'juhygt,jhgf,mjhg,', '1', 7),
(20, 3, 'fghnjmkjh', 'jhghjk,yhujniou,hgfkjnhbkj,', '1', 7),
(21, 3, 'rtfduhjgjhjftcdy', 'kkj,gfgt,ytj,', '1', 8),
(22, 3, 'frtgyluihygtfkhg', 'gytj,jklig,gt5t6u7i,', '2', 8),
(23, 3, 'rtfduhjgjhjftcdy', 'kkj,gfgt,ytj,', '1', 9),
(24, 3, 'frtgyluihygtfkhg', 'gytj,jklig,gt5t6u7i,', '2', 9),
(25, 3, 'rtfduhjgjhjftcdy', 'kkj,gfgt,ytj,', '1', 10),
(26, 3, 'frtgyluihygtfkhg', 'gytj,jklig,gt5t6u7i,', '2', 10),
(27, 3, 'rtfduhjgjhjftcdy', 'kkj,gfgt,ytj,', '1', 11),
(28, 3, 'frtgyluihygtfkhg', 'gytj,jklig,gt5t6u7i,', '2', 11);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_id` varchar(20) NOT NULL,
  `academic_year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `academic_year`) VALUES
('121', 3),
('158544', 3),
('4655', 2),
('5412', 4),
('54121', 2),
('545', 3);

-- --------------------------------------------------------

--
-- Table structure for table `student_marks`
--

CREATE TABLE `student_marks` (
  `student_id` varchar(20) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `student_mark` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_marks`
--

INSERT INTO `student_marks` (`student_id`, `quiz_id`, `student_mark`) VALUES
('121', 1, 7),
('121', 2, 8),
('545', 1, 8),
('545', 2, 5),
('4655', 2, 9),
('54121', 1, 7),
('54121', 2, 8),
('5412', 1, 9),
('5412', 2, 3),
('121', 5, 0),
('545', 5, 0),
('54121', 6, 6),
('158544', 7, 6),
('121', 8, 10),
('158544', 11, 10);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `user_name` varchar(60) NOT NULL,
  `user_email` varchar(60) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_department` varchar(60) NOT NULL,
  `user_gender` varchar(60) NOT NULL,
  `user_phone` varchar(60) NOT NULL,
  `user_status` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_email`, `user_password`, `user_department`, `user_gender`, `user_phone`, `user_status`) VALUES
('121', 'sasa', 'eda@sad.com', '123', 'CS', 'Female', '1231', 'Client'),
('123', 'amr', 'amr@hotmail.com', '111', 'CS', 'male', '1485105', 'Server'),
('123456', 'kkk', 'ggg@yyy.nnn', '123', 'CS', 'male', '0124502', 'Server'),
('158544', 'iii', 'ihgfdrfg@yyy.von', '123', 'CS', 'Male', '01258256', 'Client'),
('4655', 'asd', 'ddfsd@asdasd.com', '123', 'CS', 'Male', '34234234', 'Client'),
('5412', 'omar', 'dsfsd@sdasd.com', '123', 'CS', 'Male', '21312', 'Client'),
('54121', 'saad', 'asdad@sddasd.com', '123', 'CS', 'Male', '6521321321', 'Client'),
('545', 'qwe', 'asd@asdasd.com', '123', 'CS', 'Male', '4561', 'Client');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absence`
--
ALTER TABLE `absence`
  ADD KEY `student_id` (`student_id`),
  ADD KEY `lecture_id` (`lecture_id`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`doctor_id`);

--
-- Indexes for table `lecture`
--
ALTER TABLE `lecture`
  ADD PRIMARY KEY (`lecture_id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indexes for table `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `note_ownerid` (`note_ownerid`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `questionownerid` (`questionownerid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quiz_id`),
  ADD KEY `lecture_id` (`lecture_id`);

--
-- Indexes for table `quizquestion`
--
ALTER TABLE `quizquestion`
  ADD PRIMARY KEY (`quizquestion_id`),
  ADD KEY `quiz_id` (`quiz_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `student_marks`
--
ALTER TABLE `student_marks`
  ADD KEY `quiz_id` (`quiz_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lecture`
--
ALTER TABLE `lecture`
  MODIFY `lecture_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1111134;
--
-- AUTO_INCREMENT for table `note`
--
ALTER TABLE `note`
  MODIFY `note_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quiz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `quizquestion`
--
ALTER TABLE `quizquestion`
  MODIFY `quizquestion_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `absence_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `absence_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `absence_ibfk_3` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`lecture_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `doctor`
--
ALTER TABLE `doctor`
  ADD CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `lecture`
--
ALTER TABLE `lecture`
  ADD CONSTRAINT `lecture_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `note_ibfk_1` FOREIGN KEY (`note_ownerid`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`questionownerid`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `quiz_ibfk_1` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`lecture_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `quizquestion`
--
ALTER TABLE `quizquestion`
  ADD CONSTRAINT `quizquestion_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student_marks`
--
ALTER TABLE `student_marks`
  ADD CONSTRAINT `student_marks_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `student_marks_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
