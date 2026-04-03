open Model

(******************* MEETING **********************)

let isMeetingTypeIs (m:meeting)(x:meeting_type):bool = 
  m.meeting_type = x

let isDurationGreaterThan (m:meeting)(x:int):bool = 
  m.duration_min > x

(******************** ISSUE ***********************)

let isIssuePriorityIs (i:issue)(x:issue_priority):bool = 
  i.priority = x

let isIssuePriorityIn (i:issue)(xs:issue_priority list):bool = 
  List.exists (fun x -> i.priority = x) xs

let isIssueUnderstoodOnlyBy(i: issue) (u: understanding): bool =   
  i.understood_by = [u]

let isIssueUnderstoodByList (i: issue) (ul: understanding list): bool = 
  i.understood_by = ul

(***************** PARTICIPANTS *******************)

let isExistsInterestedParticipantWithRole (ps: participant list)(rl: role list): bool = 
  List.exists (fun p -> List.mem p.role rl && p.interested) ps

let isNoInterestedParticipantWithRole (ps: participant list)(rl: role list): bool = 
  not (isExistsInterestedParticipantWithRole ps rl)



