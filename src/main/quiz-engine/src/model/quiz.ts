import { Question } from './question'

export class Quiz {
  readonly questions: Question[] = []

  private constructor(questions: Question[]) {
    this.questions = questions;
  }


  reset () {
    this.questions.forEach(it => it.reset())
  }

  static fromMarkdown (markdown: string): Quiz {
    const markdownQuestions = markdown.split('\n\n')

    const questions = markdownQuestions.map(Question.fromMarkdown)

    return new Quiz(questions)
  }
}
