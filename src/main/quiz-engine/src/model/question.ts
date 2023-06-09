import { Answer } from './answer'

export class Question {
  readonly text: string
  readonly answers: Answer[]
  private answered: boolean = false

  private constructor (text: string, answers: Answer[]) {
    this.text = text
    this.answers = answers
  };

  /**
   * parses markdown content to a question.
   * the first line is the question text, and should be marked with a markdown title '#'.
   * lines marked with '- [ ]' or '- [x]' are either incorrect or correct answers.
   * lines markes with '>' are the explanation of the answer (if any).
   * @param markdown
   */
  static fromMarkdown (markdown: string): Question {
    const lines = markdown.split('\n').map(it => it.trim()).filter(it => it.length !== 0)
    console.log(lines)
    const questionText = lines[0].slice(2)

    const answers = lines.slice(1)
      .map(it => Answer.fromMarkdown(it))
    answers
      .forEach((it, idx) => it.id = idx)
    return new Question(questionText, answers)
  }

  answer () {
    this.answered = true
  }

  isAnswered () {
    return this.answered
  }

  /**
   * Computes the score of the question, one good answer = one point
   */
  score(): number {
    return this.answers.filter(it => it.selected && it.correct).length
  }

  reset () {
    this.answered = false;
    this.answers.forEach(it => it.selected = false)
  }
}
