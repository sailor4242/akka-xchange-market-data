package org.dzavorin.xchange.utils

import scala.collection.mutable

/**
  * Queue that holds particular amount of elements, when the maximum is reached, the oldest ones are removed
  * @param maxSize the desirable maximum of the queue
  * @tparam A
  */
class LimitedQueue[A](maxSize: Int) extends mutable.Queue[A] {

  def enqueue(elems: A): Unit = {
    super.enqueue(elems)
    while (size > maxSize) super.dequeue
  }

}