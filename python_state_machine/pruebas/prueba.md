# Scenario Report

## Scenario
- **ID:** SCN-001
- **Status:** ok
- **Final state:** EntropyAbandoned
- **Final context:** revival_signals=1 qa_rejections=1 sprints_ignored=2
    - revival_signals: 1
    - qa_rejections: 1
    - sprints_ignored: 2

## Events
1. ClarifySomehow
    - resulting_state: PretendPlanning 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 0
2. StartAnyway
    - resulting_state: HeroicImplementation 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 0
3. DiscoverDisagreement
    - resulting_state: PhilosophicalDebate 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 0
4. Postpone
    - resulting_state: TemporarilyPostponed 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 0
5. ForgetForLongTime
    - resulting_state: TemporarilyPostponed 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 1
6. ForgetForLongTime
    - resulting_state: ZombieFeature 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 2
7. CustomerComplains
    - resulting_state: ZombieFeature 
    - context:
        - revival_signals: 1
        - qa_rejections: 0
        - sprints_ignored: 2
8. ExecutiveRemembers
    - resulting_state: HeroicImplementation 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 2
9. DiscoverDisagreement
    - resulting_state: PhilosophicalDebate 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 2
10. StartAnyway
    - resulting_state: HeroicImplementation 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 2
11. SendToQA
    - resulting_state: StressTheThing 
    - context:
        - revival_signals: 0
        - qa_rejections: 0
        - sprints_ignored: 2
12. Rework
    - resulting_state: HeroicImplementation 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 2
13. SendToQA
    - resulting_state: StressTheThing 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 2
14. RejectFundamentally
    - resulting_state: PhilosophicalDebate 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 2
15. Postpone
    - resulting_state: TemporarilyPostponed 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 0
16. ForgetForLongTime
    - resulting_state: TemporarilyPostponed 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 1
17. ForgetForLongTime
    - resulting_state: ZombieFeature 
    - context:
        - revival_signals: 0
        - qa_rejections: 1
        - sprints_ignored: 2
18. AuditDiscovers
    - resulting_state: ZombieFeature 
    - context:
        - revival_signals: 1
        - qa_rejections: 1
        - sprints_ignored: 2
19. DeclareEntropyAbandoned
    - resulting_state: EntropyAbandoned 
    - context:
        - revival_signals: 1
        - qa_rejections: 1
        - sprints_ignored: 2
